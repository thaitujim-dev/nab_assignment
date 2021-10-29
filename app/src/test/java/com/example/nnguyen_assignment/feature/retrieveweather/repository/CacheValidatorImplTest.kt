package com.example.nnguyen_assignment.feature.retrieveweather.repository

import com.example.nnguyen_assignment.feature.retrieveweather.repository.datasource.ForecastLocalDataSourceImpl
import org.junit.Assert.*

import org.junit.Test
import java.util.*

class CacheValidatorImplTest {

    @Test
    fun `cache is no validated`() {
        val cacheTimeLived = Date().time - ForecastLocalDataSourceImpl.CACHE_TIME_LIVE - 1000 // 1 min later

        val actual = CacheValidatorImpl().isCacheValid(cacheTimeLived)
        assertFalse(actual)
    }

    @Test
    fun `cache is validated`() {
        val cacheTimeLived = Date().time - ForecastLocalDataSourceImpl.CACHE_TIME_LIVE + 1000 // 1 min sooner

        val actual = CacheValidatorImpl().isCacheValid(cacheTimeLived)
        assertTrue(actual)
    }
}