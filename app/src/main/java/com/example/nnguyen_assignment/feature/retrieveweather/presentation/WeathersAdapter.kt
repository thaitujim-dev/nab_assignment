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
                textDate.text =
                    context.getString(R.string.forecast_date, formatter.format(Date(forecast.dt)))
                textTemp.text = context.getString(
                    R.string.forecast_average_temperature,
                    forecast.temp.toAverageTemperature().roundToInt().toString()
                )
                textPressure.text =
                    context.getString(R.string.forecast_pressure, forecast.pressure.toString())
                textHumidity.text =
                    context.getString(R.string.forecast_humidity, forecast.humidity.toString())
                        .plus("%")
                textDescription.text = context.getString(
                    R.string.forecast_description,
                    forecast.weather.firstOrNull()?.description.orEmpty()
                )
            }
        }
    }
}
