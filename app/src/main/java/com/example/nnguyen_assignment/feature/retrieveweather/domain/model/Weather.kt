package com.example.nnguyen_assignment.feature.retrieveweather.domain.model

data class Weather(
    val id: Long = 0L,
    val main: String = "",
    val description: String = "",
    val icon: String = ""
)