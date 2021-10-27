package com.example.nnguyen_assignment.feature.retrieveweather.domain.usecase

import com.example.nnguyen_assignment.core.exception.Failure
import com.example.nnguyen_assignment.core.functional.Either
import com.example.nnguyen_assignment.core.interactor.UseCase
import com.example.nnguyen_assignment.feature.retrieveweather.domain.ForecastFailure
import com.example.nnguyen_assignment.feature.retrieveweather.domain.ForecastRepository
import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.Forecast
import com.example.nnguyen_assignment.feature.retrieveweather.presentation.InputValidator
import javax.inject.Inject

class GetForecastUseCase
@Inject constructor(
    private val inputValidator: InputValidator,
    private val forecastRepository: ForecastRepository
) :
    UseCase<List<Forecast>, GetForecastUseCaseParams>() {

    override suspend fun run(params: GetForecastUseCaseParams): Either<Failure, List<Forecast>> {
        return if (inputValidator.isInputValid(params.city)) {
            forecastRepository.getForecast(params.city.trim(), params.count)
        } else {
            Either.Left(ForecastFailure.InputFailure())
        }

    }

}