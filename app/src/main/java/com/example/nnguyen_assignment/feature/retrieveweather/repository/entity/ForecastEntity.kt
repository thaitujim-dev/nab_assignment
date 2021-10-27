package com.example.nnguyen_assignment.feature.retrieveweather.repository.entity

import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.FeelsLike
import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.Forecast
import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.Temp


data class ForecastEntity(
    val dt: Long?,
    val sunrise: Long?,
    val sunset: Long?,
    val temp: TempEntity?,
    val feels_like: FeelsLikeEntity?,
    val pressure: Long?,
    val humidity: Long?,
    val weather: List<WeatherEntity>?,
    val speed: Float?,
    val deg: Long?,
    val gust: Float?,
    val clouds: Long?,
    val pop: Float?,
    val rain: Float?,
) {
    fun transform() = Forecast(
        dt = dt ?: 0,
        sunrise = sunrise ?: 0,
        sunset = sunset ?: 0,
        temp = temp?.transform() ?: Temp(),
        feelsLike = feels_like?.transform() ?: FeelsLike(),
        pressure = pressure ?: 0,
        humidity = humidity ?: 0,
        weather = weather?.map { it.transform() } ?: emptyList(),
        speed = speed ?: 0F,
        deg = deg ?: 0L,
        gust = gust ?: 0F,
        clouds = clouds ?: 0L,
        pop = pop ?: 0F,
        rain = rain ?: 0F,
    )
}