package com.compose.newsapp.utils

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null,
) {
    class Success<T>(data: T?) : NetworkResult<T>(data)

    class Error<T>(message: String) : NetworkResult<T>(null, message)

    class Loading<T>(val isLoading: Boolean = true) : NetworkResult<T>(null)
}