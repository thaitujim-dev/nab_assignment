package com.example.nnguyen_assignment.feature.retrieveweather.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nnguyen_assignment.core.platform.BaseViewModel
import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.Forecast
import com.example.nnguyen_assignment.feature.retrieveweather.domain.usecase.GetForecastUseCase
import com.example.nnguyen_assignment.feature.retrieveweather.domain.usecase.GetForecastUseCaseParams
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(val getForecastUseCase: GetForecastUseCase) :
    BaseViewModel() {

    private val _forecasts: MutableLiveData<List<Forecast>> = MutableLiveData()
    val forecasts: LiveData<List<Forecast>> = _forecasts

    fun loadForecasts(city: String, count: Int) {
        val params = GetForecastUseCaseParams(city, count)

        getForecastUseCase(params, viewModelScope) {
            it.fold(::handleFailure, ::handleForecastList)
        }
    }


    private fun handleForecastList(forecasts: List<Forecast>) {
        _forecasts.value = forecasts
    }
}
