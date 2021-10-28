package com.example.nnguyen_assignment.feature.retrieveweather.repository.datasource

import com.example.nnguyen_assignment.core.platform.RequestBuilder
import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.Forecast
import com.example.nnguyen_assignment.feature.retrieveweather.repository.ForecastRepositoryImpl
import com.example.nnguyen_assignment.feature.retrieveweather.repository.ForecastService
import com.example.nnguyen_assignment.feature.retrieveweather.repository.entity.ForecastResponseEntity
import javax.inject.Inject

interface ForecastRemoteDataSource {
    fun getForecast(city: String, count: Int): List<Forecast>
}

class ForecastRemoteDataSourceImpl @Inject constructor(
    private val forecastService: ForecastService
) :
    ForecastRemoteDataSource {
    override fun getForecast(city: String, count: Int): List<Forecast> {
        return RequestBuilder.request(
            call = forecastService.getForecast(
                city,
                count,
                ForecastRepositoryImpl.APP_ID,
                ForecastRepositoryImpl.UNIT
            ),
            transform = { it ->
                it.list?.map { it.transform() } ?: emptyList()
            },
            default = ForecastResponseEntity.empty()
        )
    }


}