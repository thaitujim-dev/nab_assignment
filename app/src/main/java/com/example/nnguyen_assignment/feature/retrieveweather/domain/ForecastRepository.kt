package com.example.nnguyen_assignment.feature.retrieveweather.domain

import com.example.nnguyen_assignment.core.exception.Failure
import com.example.nnguyen_assignment.core.functional.Either
import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.Forecast
import com.example.nnguyen_assignment.feature.retrieveweather.repository.ForecastRepositoryImpl.Companion.COUNT

interface ForecastRepository {
    fun getForecast(city: String, count: Int = COUNT): Either<Failure, List<Forecast>>
}