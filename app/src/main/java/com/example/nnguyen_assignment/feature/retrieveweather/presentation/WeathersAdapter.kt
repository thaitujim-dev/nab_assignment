package com.example.nnguyen_assignment.feature.retrieveweather.presentation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nnguyen_assignment.R
import com.example.nnguyen_assignment.core.extension.inflate
import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.Forecast
import kotlinx.android.synthetic.main.row_forecast.view.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlin.properties.Delegates

class WeathersAdapter
@Inject constructor() : RecyclerView.Adapter<WeathersAdapter.ViewHolder>() {

    internal var collection: List<Forecast> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.row_forecast))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position])

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(forecast: Forecast) {

            val formatter = SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault())

            itemView.apply {
                context.getString(R.string.forecast_date, formatter.format(Date(forecast.dt))).let {
                    textDate.text = it
                    textDate.contentDescription = it
                }
                context.getString(
                    R.string.forecast_average_temperature,
                    forecast.temp.toAverageTemperature().roundToInt().toString()
                ).let {
                    textTemp.text = it
                    textTemp.contentDescription = it
                }

                context.getString(R.string.forecast_pressure, forecast.pressure.toString()).let {
                    textPressure.text = it
                    textPressure.contentDescription = it
                }

                context.getString(R.string.forecast_humidity, forecast.humidity.toString())
                    .plus("%").let {
                    textHumidity.text = it
                    textHumidity.contentDescription = it
                }

                context.getString(
                    R.string.forecast_description,
                    forecast.weather.firstOrNull()?.description.orEmpty().let {
                        textDescription.text = it
                        textDescription.contentDescription = it
                    }

                )
            }
        }
    }
}
