package com.example.nnguyen_assignment.feature.retrieveweather.presentation

import javax.inject.Inject

interface InputValidator {
    fun isInputValid(input: String): Boolean
}

class InputValidatorImpl @Inject constructor() : InputValidator {
    companion object {
        const val MINIMUM_INPUT_LENGTH = 3
    }

    override fun isInputValid(input: String) = input.trim().length >= MINIMUM_INPUT_LENGTH
}