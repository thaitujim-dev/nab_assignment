package com.example.nnguyen_assignment.feature.retrieveweather.domain.model

import com.example.nnguyen_assignment.feature.retrieveweather.repository.entity.CityEntity
import com.example.nnguyen_assignment.feature.retrieveweather.repository.entity.ForecastEntity

data class ForecastResponse(
    val city: CityEntity,
    val cod: String,
    val message: Float,
    val cnt: Int,
    val list: List<ForecastEntity>
)