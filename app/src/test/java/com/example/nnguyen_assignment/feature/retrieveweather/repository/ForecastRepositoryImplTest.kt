package com.example.nnguyen_assignment.feature.retrieveweather.repository

import com.example.nnguyen_assignment.core.exception.Exception
import com.example.nnguyen_assignment.core.exception.Failure
import com.example.nnguyen_assignment.core.functional.Either
import com.example.nnguyen_assignment.core.platform.NetworkHandler
import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.FeelsLike
import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.Forecast
import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.Temp
import com.example.nnguyen_assignment.feature.retrieveweather.repository.datasource.ForecastLocalDataSource
import com.example.nnguyen_assignment.feature.retrieveweather.repository.datasource.ForecastRemoteDataSource
import com.nhaarman.mockitokotlin2.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString


import org.mockito.Mockito.`when`

class ForecastRepositoryImplTest {

    private lateinit var mockForecastLocalDataSource: ForecastLocalDataSource
    private lateinit var mockForecastRemoteDataSource: ForecastRemoteDataSource
    private lateinit var mockNetworkHandler: NetworkHandler
    private lateinit var forecastRepositoryImpl: ForecastRepositoryImpl

    @Before
    fun setUp() {
        mockForecastLocalDataSource = mock()
        mockForecastRemoteDataSource = mock()
        mockNetworkHandler = mock()
        forecastRepositoryImpl = ForecastRepositoryImpl(
            mockForecastLocalDataSource,
            mockForecastRemoteDataSource,
            mockNetworkHandler
        )
    }

    @After
    fun tearDown() {
        reset(mockForecastLocalDataSource)
        reset(mockForecastRemoteDataSource)
        reset(mockNetworkHandler)
    }

    private fun createListForecast() = listOf(
        Forecast(
            dt = 0L,
            sunrise = 0L,
            sunset = 0L,
            temp = Temp(),
            feelsLike = FeelsLike(),
            pressure = 0L,
            humidity = 0L,
            weather = listOf(),
            speed = 0F,
            deg = 0L,
            gust = 0F,
            clouds = 0L,
            pop = 0F,
            rain = 0F,
        )
    )

    @Test
    fun `get forecast should return a Left of NetworkConnection when network is not available`() {
        //arrange
        `when`(mockNetworkHandler.isNetworkAvailable()).thenReturn(false)
        val expected = Either.Left(Failure.NetworkConnection)
        //act
        val actual = forecastRepositoryImpl.getForecast(city = "city")
        //expect
        assertEquals(expected, actual)
        verify(mockNetworkHandler, times(1)).isNetworkAvailable()
        verify(mockForecastLocalDataSource, never()).getCacheForecast(anyString())
        verify(mockForecastRemoteDataSource, never()).getForecast(anyString(), anyInt())
    }

    @Test
    fun `get forecast should return a Right of forecast data when local data source available`() {
        //arrange
        val listForecast = createListForecast()
        `when`(mockNetworkHandler.isNetworkAvailable()).thenReturn(true)
        `when`(mockForecastLocalDataSource.getCacheForecast("city")).thenReturn(listForecast)

        val expected = Either.Right(listForecast)
        //act
        val actual = forecastRepositoryImpl.getForecast(city = "city")
        //expect
        assertEquals(expected, actual)
        verify(mockNetworkHandler, times(1)).isNetworkAvailable()
        verify(mockForecastLocalDataSource, times(1)).getCacheForecast("city")
        verify(mockForecastRemoteDataSource, never()).getForecast(anyString(), anyInt())
    }

    @Test
    fun `get forecast should return a Right of forecast data when local data source not available and remote data source returned data`() {
        //arrange
        val listForecast = createListForecast()
        `when`(mockNetworkHandler.isNetworkAvailable()).thenReturn(true)
        `when`(mockForecastLocalDataSource.getCacheForecast(eq("city"))).thenAnswer { throw Exception.NoneCacheException() }
        `when`(mockForecastRemoteDataSource.getForecast(anyString(), anyInt())).thenReturn(
            listForecast
        )

        val expected = Either.Right(listForecast)
        //act
        val actual = forecastRepositoryImpl.getForecast(city = "city")
        //expect
        assertEquals(expected, actual)
        verify(mockNetworkHandler, times(1)).isNetworkAvailable()
        verify(mockForecastLocalDataSource, times(1)).getCacheForecast("city")
        verify(mockForecastRemoteDataSource, times(1)).getForecast(anyString(), anyInt())
    }

    @Test
    fun `get forecast should put to cache when get forecast from remote data source success`() {
        //arrange
        val listForecast = createListForecast()
        `when`(mockNetworkHandler.isNetworkAvailable()).thenReturn(true)
        `when`(mockForecastLocalDataSource.getCacheForecast(eq("city"))).thenAnswer { throw Exception.NoneCacheException() }
        `when`(mockForecastRemoteDataSource.getForecast(eq("city"), anyInt())).thenReturn(
            listForecast
        )
        `when`(
            mockForecastLocalDataSource.cacheForecast(
                eq("city"),
                eq(listForecast)
            )
        ).thenAnswer { Unit }

        val expected = Either.Right(listForecast)
        //act
        val actual = forecastRepositoryImpl.getForecast(city = "city")
        //expect
        assertEquals(expected, actual)
        verify(mockNetworkHandler, times(1)).isNetworkAvailable()
        verify(mockForecastLocalDataSource, times(1)).getCacheForecast("city")
        verify(mockForecastRemoteDataSource, times(1)).getForecast(eq("city"), anyInt())
        verify(mockForecastLocalDataSource, times(1)).cacheForecast(eq("city"), eq(listForecast))
    }

    @Test
    fun `get forecast should return Server Error when get forecast from remote data source throw exception`() {
        //arrange
        `when`(mockNetworkHandler.isNetworkAvailable()).thenReturn(true)
        `when`(mockForecastLocalDataSource.getCacheForecast(eq("city"))).thenAnswer { throw Exception.NoneCacheException() }
        `when`(
            mockForecastRemoteDataSource.getForecast(
                eq("city"),
                anyInt()
            )
        ).thenAnswer { throw Exception.ServerException() }

        val expected = Either.Left(Failure.ServerError)
        //act
        val actual = forecastRepositoryImpl.getForecast(city = "city")
        //expect
        assertEquals(expected, actual)
        verify(mockNetworkHandler, times(1)).isNetworkAvailable()
        verify(mockForecastLocalDataSource, times(1)).getCacheForecast("city")
        verify(mockForecastRemoteDataSource, times(1)).getForecast(eq("city"), anyInt())
    }
}