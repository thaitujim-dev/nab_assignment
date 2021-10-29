package com.example.nnguyen_assignment.feature.retrieveweather.repository

import com.example.nnguyen_assignment.feature.retrieveweather.repository.datasource.ForecastLocalDataSourceImpl
import java.util.*
import javax.inject.Inject

interface CacheValidator {
    fun isCacheValid(time: Long): Boolean
}

class CacheValidatorImpl @Inject constructor() : CacheValidator {
    override fun isCacheValid(time: Long): Boolean {
        return Date().time - time < ForecastLocalDataSourceImpl.CACHE_TIME_LIVE
    }

}