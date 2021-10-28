package com.example.nnguyen_assignment.feature.retrieveweather.repository.datasource

import com.example.nnguyen_assignment.core.exception.Exception
import com.example.nnguyen_assignment.feature.retrieveweather.domain.model.Forecast
import java.util.*
import javax.inject.Inject

interface ForecastLocalDataSource {
    fun cacheForecast(city: String, data: List<Forecast>)
    fun getCacheForecast(city: String): List<Forecast>
}

class ForecastLocalDataSourceImpl @Inject constructor() :
    ForecastLocalDataSource {

    private var _cache: LinkedHashMap<String, Pair<Long, List<Forecast>>> =
        LinkedHashMap<String, Pair<Long, List<Forecast>>>()

    companion object {
        const val MAX_SIZE_CACHE = 1000
        const val REDUCE_CACHE_SIZE = 100
        const val CACHE_TIME_LIVE = 1 * 60 * 1000 //1 min
    }

    override fun cacheForecast(city: String, data: List<Forecast>) {
        val cacheForecast = Pair(Date().time, data)
        reduceCache()
        _cache[city] = cacheForecast
    }

    private fun reduceCache() {
        if (_cache.size > MAX_SIZE_CACHE) {
            _cache.keys.let { keys ->
                for (i in 0..REDUCE_CACHE_SIZE)
                    _cache.remove(keys.elementAt(i))
            }

        }
    }

    override fun getCacheForecast(city: String): List<Forecast> {
        val cacheForecast = _cache[city]
        cacheForecast?.let {
            if (Date().time - cacheForecast.first < CACHE_TIME_LIVE) {
                return cacheForecast.second
            } else {
                _cache.remove(city)
                throw Exception.NoneCacheException()
            }
        } ?: throw Exception.NoneCacheException()
    }

}