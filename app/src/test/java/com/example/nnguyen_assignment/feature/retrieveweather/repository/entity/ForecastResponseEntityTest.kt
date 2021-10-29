package com.example.nnguyen_assignment.feature.retrieveweather.repository.entity

import com.google.gson.Gson
import org.junit.Assert
import org.junit.Test
import java.io.FileReader
import java.nio.file.Paths


class ForecastResponseEntityTest {

    @Test
    fun `Forecast response should be parsed for valid json`() {
        val resourceDirectory = Paths.get("src", "test", "resources")
        val absolutePath = resourceDirectory.toFile().absolutePath

        val gson = Gson()
        val forecastResponseEntity = gson.fromJson(
            FileReader("${absolutePath}/json_test.json"),
            ForecastResponseEntity::class.java
        )

        Assert.assertEquals("Ho Chi Minh City", forecastResponseEntity.city?.name)
        //run test without exception meaning parse json success
    }
}