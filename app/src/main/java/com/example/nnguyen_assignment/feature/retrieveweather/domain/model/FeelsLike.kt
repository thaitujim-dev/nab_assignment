package com.example.nnguyen_assignment.feature.retrieveweather.domain.model

data class FeelsLike(
    val day: Float = 0F,
    val night: Float = 0F,
    val eve: Float = 0F,
    val morn: Float = 0F
)