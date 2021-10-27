package com.example.nnguyen_assignment.feature.retrieveweather.repository.entity

import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.Temp

data class TempEntity(
    val day: Float?,
    val min: Float?,
    val max: Float?,
    val night: Float?,
    val eve: Float?,
    val morn: Float?
) {
    fun transform() = Temp(
        day = day ?: 0F,
        min = min ?: 0F,
        max = max ?: 0F,
        night = night ?: 0F,
        eve = eve ?: 0F,
        morn = morn ?: 0F,
    )
}