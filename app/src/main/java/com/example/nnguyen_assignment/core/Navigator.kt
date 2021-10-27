package com.example.nnguyen_assignment.core

import android.content.Context
import com.example.nnguyen_assignment.feature.retrieveweather.presentation.WeatherActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {
    fun showMain(context: Context) = context.startActivity(WeatherActivity.callingIntent(context))
}