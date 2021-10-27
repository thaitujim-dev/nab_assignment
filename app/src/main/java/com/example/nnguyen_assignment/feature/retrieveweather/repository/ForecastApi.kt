package com.example.nnguyen_assignment.feature.retrieveweather.repository

import com.example.nnguyen_assignment.feature.retrieveweather.repository.entity.ForecastResponseEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {

    @GET("/data/2.5/forecast/daily")
    fun getForecast(
        @Query("q") city: String,
        @Query("cnt") count: Int,
        @Query("appid") appId: String,
        @Query("units") units: String
    ): Call<ForecastResponseEntity>
}