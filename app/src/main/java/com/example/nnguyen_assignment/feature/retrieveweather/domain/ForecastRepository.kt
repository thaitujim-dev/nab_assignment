package com.example.nnguyen_assignment.feature.retrieveweather.domain

import com.example.nnguyen_assignment.core.exception.Failure
import com.example.nnguyen_assignment.core.functional.Either
import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.Forecast

interface ForecastRepository {
    fun getForecast(city: String, count: Int): Either<Failure, List<Forecast>>
}