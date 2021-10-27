package com.example.nnguyen_assignment.feature.retrieveweather.domain.model

data class Temp(
    val day: Float = 0F,
    val min: Float = 0F,
    val max: Float = 0F,
    val night: Float = 0F,
    val eve: Float = 0F,
    val morn: Float = 0F
) {
    fun toAverageTemperature() = (min + max / 2) * 100 / 100
}