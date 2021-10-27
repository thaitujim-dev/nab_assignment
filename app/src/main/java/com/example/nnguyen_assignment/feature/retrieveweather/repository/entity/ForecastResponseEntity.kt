package com.example.nnguyen_assignment.feature.retrieveweather.repository.entity

data class ForecastResponseEntity(
    val city: CityEntity?,
    val cod: String?,
    val message: Float?,
    val cnt: Int?,
    val list: List<ForecastEntity>?
) {
    companion object {
        fun empty() = ForecastResponseEntity(
            city = null,
            cod = null,
            message = null,
            cnt = null,
            list = null,
        )
    }
}