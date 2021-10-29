package com.example.nnguyen_assignment.feature.retrieveweather.repository.datasource

import com.example.nnguyen_assignment.core.exception.Exception
import com.example.nnguyen_assignment.feature.retrieveweather.repository.ForecastApi
import com.example.nnguyen_assignment.feature.retrieveweather.repository.entity.*
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Call
import retrofit2.Response.error
import retrofit2.Response.success

class ForecastRemoteDataSourceImplTest {

    private lateinit var forecastApi: ForecastApi
    private lateinit var forecastRemoteDataSourceImpl: ForecastRemoteDataSourceImpl
    private lateinit var mockForecastServiceCall: Call<ForecastResponseEntity>

    @Before
    fun setUp() {
        forecastApi = mock()
        mockForecastServiceCall = mock()
        forecastRemoteDataSourceImpl = ForecastRemoteDataSourceImpl(forecastApi)
    }

    @After
    fun tearDown() {
        reset(forecastApi)
        reset(mockForecastServiceCall)
    }

    private fun createListForecastResponse() = ForecastResponseEntity(
        city = CityEntity(
            id = 0,
            name = "city",
            coord = CoordEntity(0.0, 0.0),
            country = "",
            population = "",
            timezone = 0,

            ),
        cod = "",
        message = 0F,
        cnt = 1,
        list = listOf(
            ForecastEntity(
                dt = 0L,
                sunrise = 0L,
                sunset = 0L,
                temp = TempEntity(
                    day = 0F,
                    min = 0F,
                    max = 0F,
                    night = 0F,
                    eve = 0F,
                    morn = 0F
                ),
                feels_like = FeelsLikeEntity(
                    day = 0F,
                    night = 0F,
                    eve = 0F,
                    morn = 0F
                ),
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
        ),
    )


    @Test
    fun `remoteDataSource should return data when service return a success response`() {
        val data = createListForecastResponse()
        val response = success(data)
        `when`(forecastApi.getForecast(anyString(), anyInt(), anyString(), anyString())).thenReturn(
            mockForecastServiceCall
        )
        `when`(mockForecastServiceCall.execute()).thenReturn(response)

        val expected = data.list?.map { it.transform() } ?: emptyList()

        val actual = forecastRemoteDataSourceImpl.getForecast("city", 0)

        assertEquals(expected, actual)

        verify(forecastApi, times(1)).getForecast(anyString(), anyInt(), anyString(), anyString())
        verify(mockForecastServiceCall, times(1)).execute()
    }

    @Test
    fun `remoteDataSource should throw ServerException when service return a unSuccess response`() {
        Assert.assertThrows(Exception.ServerException::class.java) {
            val response = error<ForecastResponseEntity>(
                401, "".toResponseBody("application/json".toMediaTypeOrNull())
            )
            `when`(
                forecastApi.getForecast(
                    anyString(),
                    anyInt(),
                    anyString(),
                    anyString()
                )
            ).thenReturn(
                mockForecastServiceCall
            )
            `when`(mockForecastServiceCall.execute()).thenReturn(response)

            forecastRemoteDataSourceImpl.getForecast("city", 0)

            verify(forecastApi, times(1)).getForecast(
                anyString(),
                anyInt(),
                anyString(),
                anyString()
            )
            verify(mockForecastServiceCall, times(1)).execute()
        }
    }


}