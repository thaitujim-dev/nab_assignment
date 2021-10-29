package com.example.nnguyen_assignment.feature.retrieveweather.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nnguyen_assignment.core.functional.Either
import com.example.nnguyen_assignment.feature.retrieveweather.domain.usecase.GetForecastUseCase
import com.example.nnguyen_assignment.feature.retrieveweather.domain.usecase.MainCoroutineRule
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
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
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var useCase: GetForecastUseCase

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        useCase = mock()
        viewModel = WeatherViewModel(
            useCase
        )
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        reset(useCase)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `loadForecasts should call the usecase`() {
        testDispatcher.runBlockingTest {
            `when`(useCase.run(anyOrNull())).thenReturn(Either.Right(emptyList()))
            viewModel.loadForecasts("city")

            viewModel.forecasts.observeForever {
                assertEquals(it.size, 0)
            }

            verify(useCase).run(anyOrNull())
        }

    }
}