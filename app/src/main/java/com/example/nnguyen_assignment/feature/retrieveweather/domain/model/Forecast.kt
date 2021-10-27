package com.example.nnguyen_assignment.feature.retrieveweather.domain.model

data class Forecast(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Temp,
    val feelsLike: FeelsLike,
    val pressure: Long,
    val humidity: Long,
    val weather: List<Weather>,
    val speed: Float,
    val deg: Long,
    val gust: Float,
    val clouds: Long,
    val pop: Float,
    val rain: Float,
)