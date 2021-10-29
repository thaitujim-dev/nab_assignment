package com.example.nnguyen_assignment.feature.retrieveweather.repository.datasource

import com.example.nnguyen_assignment.core.exception.Exception
import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.FeelsLike
import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.Forecast
import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.Temp
import com.example.nnguyen_assignment.feature.retrieveweather.repository.CacheValidator
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyLong

class ForecastLocalDataSourceImplTest {

    private lateinit var forecastLocalDataSourceImpl: ForecastLocalDataSourceImpl
    private lateinit var mockCacheValidator: CacheValidator

    @Before
    fun setUp() {
        mockCacheValidator = mock()
        forecastLocalDataSourceImpl = ForecastLocalDataSourceImpl(mockCacheValidator)
    }

    @After
    fun tearDown() {
        forecastLocalDataSourceImpl.cache.clear()
        reset(mockCacheValidator)
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
    fun `forecastLocalDataSource should put cache data by key success when call cacheForecast`() {
        //arrange
        val data = createListForecast()
        //act
        forecastLocalDataSourceImpl.cacheForecast("city1", data)
        val actualData = forecastLocalDataSourceImpl.cache["city1"]!!.second
        val actualNull = forecastLocalDataSourceImpl.cache["city2"]
        //expect
        assertEquals(data, actualData)
        assertEquals(actualNull, null)
    }

    @Test
    fun `forecastLocalDataSource should return cache data success when call cache available and valid`() {
        //arrange
        val data = createListForecast()
        `when`(mockCacheValidator.isCacheValid(anyLong())).thenReturn(true)

        //act
        forecastLocalDataSourceImpl.cacheForecast("city1", data)
        val actual = forecastLocalDataSourceImpl.cache["city1"]!!.second

        //expected
        assertEquals(data, actual)
    }

    @Test
    fun `forecastLocalDataSource should throw NoneCacheException when cache is not valid after amount time`() {
        assertThrows(Exception.NoneCacheException::class.java) {
            `when`(mockCacheValidator.isCacheValid(anyLong())).thenReturn(false)

            val data = createListForecast()
            forecastLocalDataSourceImpl.cacheForecast("city", data)

            forecastLocalDataSourceImpl.getCacheForecast("city")
        }

    }

    @Test
    fun `forecastLocalDataSource should throw NoneCacheException when cache is not available`() {
        assertThrows(Exception.NoneCacheException::class.java) {

            forecastLocalDataSourceImpl.getCacheForecast("city")
        }

    }
}