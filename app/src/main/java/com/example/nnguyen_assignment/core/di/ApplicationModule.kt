package com.example.nnguyen_assignment.core.di

import com.example.nnguyen_assignment.BuildConfig
import com.example.nnguyen_assignment.feature.retrieveweather.domain.ForecastRepository
import com.example.nnguyen_assignment.feature.retrieveweather.repository.ForecastRepositoryImpl
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
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
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
}