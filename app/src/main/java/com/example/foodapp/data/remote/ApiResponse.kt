package com.example.foodapp.data.remote

import retrofit2.Response



sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()

    data class Error(val code: Int, val message: String) : ApiResponse<Nothing>()

    data class Exception(val message: String) : ApiResponse<Nothing>()
}


suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResponse<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                ApiResponse.Success(body)
            } else {
                ApiResponse.Error(
                    code = response.code(),
                    message = "Empty response body"
                )
            }
        } else {
            ApiResponse.Error(
                code = response.code(),
                message = response.errorBody()?.string()
                    ?: "Something went wrong"
            )
        }
    } catch (e: Exception) {
        ApiResponse.Exception(
            message = e.localizedMessage ?: "Network error"
        )
    }
}