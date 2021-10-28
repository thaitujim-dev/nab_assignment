package com.example.nnguyen_assignment.core.platform

import com.example.nnguyen_assignment.core.exception.Exception
import retrofit2.Call

class RequestBuilder {

    companion object {
        fun <T, R> request(
            call: Call<T>,
            transform: (T) -> R,
            default: T
        ): R {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> transform((response.body() ?: default))
                    false -> throw  Exception.ServerException()
                }
            } catch (exception: Throwable) {
                throw  Exception.ServerException()
            }
        }
    }

}