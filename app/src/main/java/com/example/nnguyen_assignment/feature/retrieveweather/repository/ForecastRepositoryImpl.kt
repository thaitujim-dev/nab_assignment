package com.example.nnguyen_assignment.feature.retrieveweather.repository

import com.example.nnguyen_assignment.core.exception.Exception
import com.example.nnguyen_assignment.core.exception.Failure
import com.example.nnguyen_assignment.core.functional.Either
import com.example.nnguyen_assignment.core.platform.NetworkHandler
import com.example.nnguyen_assignment.feature.retrieveweather.domain.ForecastRepository
import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.Forecast
import com.example.nnguyen_assignment.feature.retrieveweather.repository.datasource.ForecastLocalDataSource
import com.example.nnguyen_assignment.feature.retrieveweather.repository.datasource.ForecastRemoteDataSource
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val forecastLocalDataSource: ForecastLocalDataSource,
    private val forecastRemoteDataSource: ForecastRemoteDataSource,
    private val networkHandler: NetworkHandler
) :
    ForecastRepository {
    companion object {
        // HIDDEN
        //const val APP_ID = "60c6fbeb4b93ac653c492ba806fc346d"
        const val UNIT = "metric"
        const val COUNT = 14
    }

    override fun getForecast(city: String, count: Int): Either<Failure, List<Forecast>> {
        when (networkHandler.isNetworkAvailable()) {
            true -> {
                return try {
                    val result = forecastLocalDataSource.getCacheForecast(city)

                    Either.Right(result)

                } catch (e: Exception.NoneCacheException) {

                    try {
                        val result = forecastRemoteDataSource.getForecast(city, count)
                        forecastLocalDataSource.cacheForecast(city, result)

                        Either.Right(result)
                    } catch (e: Exception.ServerException) {
                        Either.Left(Failure.ServerError)
                    }

                }
            }
            false -> return Either.Left(Failure.NetworkConnection)
        }


    }


}