package com.example.nnguyen_assignment.core

import android.content.Context
import com.example.nnguyen_assignment.feature.MainActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {
    fun showMain(context: Context) = context.startActivity(MainActivity.callingIntent(context))
}