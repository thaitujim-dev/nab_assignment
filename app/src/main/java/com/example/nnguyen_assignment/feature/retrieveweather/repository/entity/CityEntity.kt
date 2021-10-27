package com.example.nnguyen_assignment.feature.retrieveweather.repository.entity

data class CityEntity(
    val id: Int?,
    val name: String?,
    val coord: CoordEntity?,
    val country: String?,
    val population: String?,
    val timezone: Int?,
)