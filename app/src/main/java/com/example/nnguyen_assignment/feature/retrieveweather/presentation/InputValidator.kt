package com.example.nnguyen_assignment.feature.retrieveweather.presentation

import javax.inject.Inject

class InputValidator @Inject constructor() {
    companion object {
        const val MINIMUM_INPUT_LENGTH = 3
    }

    fun isInputValid(input: String) = input.trim().length >= MINIMUM_INPUT_LENGTH
}