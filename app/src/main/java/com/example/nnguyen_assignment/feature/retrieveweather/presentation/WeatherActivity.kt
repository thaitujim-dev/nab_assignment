package com.example.nnguyen_assignment.feature.retrieveweather.presentation

import android.content.Context
import android.content.Intent
import com.example.nnguyen_assignment.core.platform.BaseActivity

class WeatherActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, WeatherActivity::class.java)
    }

    override fun fragment() = WeatherFragment()
}