package com.example.nnguyen_assignment.feature.retrieveweather.repository.entity

import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.FeelsLike

data class FeelsLikeEntity(val day: Float?, val night: Float?, val eve: Float?, val morn: Float?) {
    fun transform() = FeelsLike(
        day = day ?: 0F,
        night = night ?: 0F,
        eve = eve ?: 0F,
        morn = morn ?: 0F
    )
}