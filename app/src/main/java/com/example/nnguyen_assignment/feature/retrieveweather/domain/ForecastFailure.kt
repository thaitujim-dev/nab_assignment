package com.example.nnguyen_assignment.feature.retrieveweather.domain

import com.example.nnguyen_assignment.core.exception.Failure

class ForecastFailure {
    class InputFailure : Failure.FeatureFailure()
}