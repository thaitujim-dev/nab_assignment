package com.example.nnguyen_assignment.feature.retrieveweather.repository.entity

import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.Weather

data class WeatherEntity(
    val id: Long?,
    val main: String?,
    val description: String?,
    val icon: String?
) {
    fun transform() = Weather(
        id = id ?: 0L,
        main = main.orEmpty(),
        description = description.orEmpty(),
        icon = icon.orEmpty()
    )
}