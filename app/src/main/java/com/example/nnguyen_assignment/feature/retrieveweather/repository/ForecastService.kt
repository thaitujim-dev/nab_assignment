package com.example.nnguyen_assignment.feature.retrieveweather.repository

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForecastService
@Inject constructor(retrofit: Retrofit) : ForecastApi {

    private val forecastApi by lazy { retrofit.create(ForecastApi::class.java) }

    override fun getForecast(
        city: String,
        count: Int,
        appId: String,
        unit: String
    ) = forecastApi.getForecast(city, count, appId, unit)
}
