package com.example.nnguyen_assignment.feature.retrieveweather.repository

import com.example.nnguyen_assignment.core.exception.Failure
import com.example.nnguyen_assignment.core.functional.Either
import com.example.nnguyen_assignment.feature.retrieveweather.domain.ForecastRepository
import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.Forecast
import com.example.nnguyen_assignment.feature.retrieveweather.repository.entity.ForecastResponseEntity
import retrofit2.Call
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(private val forecastService: ForecastService) :
    ForecastRepository {
    companion object {
        const val APP_ID = "60c6fbeb4b93ac653c492ba806fc346d"
        const val UNIT = "metric"
    }

    override fun getForecast(city: String, count: Int): Either<Failure, List<Forecast>> {
        return request(
            call = forecastService.getForecast(
                city,
                count,
                APP_ID,
                UNIT
            ),
            transform = { it ->
                it.list?.map { it.transform() } ?: emptyList()
            },
            default = ForecastResponseEntity.empty()
        )
    }


    private fun <T, R> request(
        call: Call<T>,
        transform: (T) -> R,
        default: T
    ): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> Either.Left(Failure.ServerError)
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerError)
        }
    }
}