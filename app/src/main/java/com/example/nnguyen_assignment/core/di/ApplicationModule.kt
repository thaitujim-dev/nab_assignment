package com.example.nnguyen_assignment.core.di

import com.example.nnguyen_assignment.BuildConfig
import com.example.nnguyen_assignment.core.platform.NetworkHandler
import com.example.nnguyen_assignment.core.platform.NetworkHandlerImpl
import com.example.nnguyen_assignment.feature.retrieveweather.domain.ForecastRepository
import com.example.nnguyen_assignment.feature.retrieveweather.domain.usecase.GetForecastUseCase
import com.example.nnguyen_assignment.feature.retrieveweather.domain.usecase.GetForecastUseCaseImpl
import com.example.nnguyen_assignment.feature.retrieveweather.presentation.InputValidator
import com.example.nnguyen_assignment.feature.retrieveweather.presentation.InputValidatorImpl
import com.example.nnguyen_assignment.feature.retrieveweather.repository.*
import com.example.nnguyen_assignment.feature.retrieveweather.repository.datasource.ForecastLocalDataSource
import com.example.nnguyen_assignment.feature.retrieveweather.repository.datasource.ForecastLocalDataSourceImpl
import com.example.nnguyen_assignment.feature.retrieveweather.repository.datasource.ForecastRemoteDataSource
import com.example.nnguyen_assignment.feature.retrieveweather.repository.datasource.ForecastRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {

        }

        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClientBuilder.addInterceptor(loggingInterceptor)

        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(forecastRepositoryImpl: ForecastRepositoryImpl): ForecastRepository =
        forecastRepositoryImpl

    @Provides
    @Singleton
    fun provideForecastRemoteDataSource(forecastRemoteDataSourceImpl: ForecastRemoteDataSourceImpl): ForecastRemoteDataSource =
        forecastRemoteDataSourceImpl

    @Provides
    @Singleton
    fun provideForecastLocalDataSource(forecastLocalDataSourceImpl: ForecastLocalDataSourceImpl): ForecastLocalDataSource =
        forecastLocalDataSourceImpl

    @Provides
    @Singleton
    fun provideNetworkHandler(networkHandler: NetworkHandlerImpl): NetworkHandler =
        networkHandler

    @Provides
    @Singleton
    fun provideCacheValidator(cacheValidatorImpl: CacheValidatorImpl): CacheValidator =
        cacheValidatorImpl

    @Provides
    @Singleton
    fun provideForecastService(forecastService: ForecastService): ForecastApi =
        forecastService

    @Provides
    @Singleton
    fun provideInputValidator(inputValidatorImpl: InputValidatorImpl): InputValidator =
        inputValidatorImpl

    @Provides
    fun provideGetForecastUseCase(getForecastUseCaseImpl: GetForecastUseCaseImpl): GetForecastUseCase =
        getForecastUseCaseImpl
}