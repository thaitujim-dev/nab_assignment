package com.example.nnguyen_assignment.feature.retrieveweather.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nnguyen_assignment.R
import com.example.nnguyen_assignment.core.exception.Failure
import com.example.nnguyen_assignment.core.extension.failure
import com.example.nnguyen_assignment.core.extension.invisible
import com.example.nnguyen_assignment.core.extension.observe
import com.example.nnguyen_assignment.core.extension.visible
import com.example.nnguyen_assignment.core.platform.BaseFragment
import com.example.nnguyen_assignment.feature.retrieveweather.domain.ForecastFailure
import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.Forecast
import com.scottyab.rootbeer.RootBeer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_weather.*
import javax.inject.Inject

@AndroidEntryPoint
class WeatherFragment : BaseFragment() {

    @Inject
    lateinit var weathersAdapter: WeathersAdapter

    private val moviesViewModel: WeatherViewModel by viewModels()

    override fun layoutId(): Int = R.layout.activity_weather

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(moviesViewModel) {
            observe(forecasts, ::renderForecastList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
    }

    override fun onResume() {
        super.onResume()

        checkRootDevice()
    }

    private fun initializeView() {
        weatherList.apply {
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )

            adapter = weathersAdapter
        }

        viewEmpty.visible()
        weatherList.invisible()

        buttonSearch.setOnClickListener {
            searchWeather()
        }
    }

    private fun searchWeather() {
        showProgress()
        val text = inputCity.text.toString()
        moviesViewModel.loadForecasts(text)
        hideKeyboard()
    }

    private fun hideKeyboard() {
        this.activity?.currentFocus?.let { view ->
            val imm =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun checkRootDevice() {
        context?.let {
            if (RootBeer(context).isRooted) {
                AlertDialog.Builder(it)
                    .setMessage(getString(R.string.rooted_phone_warning))
                    .setCancelable(false)
                    .setPositiveButton(R.string.alert_ok) { _, _ -> }.show()
            }
        }
    }

    private fun renderForecastList(forecasts: List<Forecast>?) {
        hideProgress()
        if (forecasts.isNullOrEmpty()) {
            viewEmpty.visible()
            weatherList.invisible()

        } else {
            viewEmpty.invisible()
            weatherList.visible()
            weathersAdapter.collection = forecasts
        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> renderFailureWithActionRefresh(R.string.failure_network_connection)
            is Failure.ServerError -> renderFailureWithActionRefresh(R.string.failure_server_error)
            is ForecastFailure.InputFailure -> renderFailure(R.string.failure_input_min_three_characters)
            else -> hideProgress()
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        hideProgress()
        notify(R.string.failure_input_min_three_characters)
    }

    private fun renderFailureWithActionRefresh(@StringRes message: Int) {
        weatherList.invisible()
        viewEmpty.visible()
        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::searchWeather)
    }
}