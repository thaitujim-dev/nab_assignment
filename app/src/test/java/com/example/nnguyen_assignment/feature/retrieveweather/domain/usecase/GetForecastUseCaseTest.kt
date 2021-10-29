package com.example.nnguyen_assignment.feature.retrieveweather.domain.usecase

import com.example.nnguyen_assignment.core.functional.Either
import com.example.nnguyen_assignment.feature.retrieveweather.domain.ForecastFailure
import com.example.nnguyen_assignment.feature.retrieveweather.domain.ForecastRepository
import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.Forecast
import com.example.nnguyen_assignment.feature.retrieveweather.presentation.InputValidator
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class GetForecastUseCaseTest {
    private lateinit var inputValidator: InputValidator
    private lateinit var forecastRepository: ForecastRepository
    private lateinit var getForecastUseCase: GetForecastUseCase

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        inputValidator = mock()
        forecastRepository = mock()
        getForecastUseCase = GetForecastUseCaseImpl(
            inputValidator,
            forecastRepository,
        )
        Dispatchers.setMain(testDispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        reset(inputValidator)
        reset(forecastRepository)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `use case should return a Left of InputFailure when validator validate invalid input`() {
        coroutineRule.runBlockingTest {
            //arrange
            `when`(inputValidator.isInputValid(anyString())).thenReturn(false)
            val params = GetForecastUseCaseParams("any city")
            val expected = Either.Left(ForecastFailure.InputFailure())
            //act
            getForecastUseCase.invoke(params, onResult = { actual ->
                //expect
                assertEquals(expected, actual)
            })
        }

    }

    @Test
    fun `use case should call of repository when validator validate input`() {
        coroutineRule.runBlockingTest {
            //arrange
            `when`(inputValidator.isInputValid(anyString())).thenReturn(true)
            `when`(forecastRepository.getForecast(anyString(), anyInt())).thenReturn(
                Either.Right(
                    emptyList()
                )
            )
            val params = GetForecastUseCaseParams("any city")
            val expected = Either.Right<List<Forecast>>(emptyList())
            //act
            getForecastUseCase.invoke(params, onResult = { actual ->
                //expect
                assertEquals(expected, actual)
            })
            verify(inputValidator, times(1)).isInputValid(anyString())
            verify(forecastRepository, times(1)).getForecast(anyString(), anyInt())
        }

    }
}