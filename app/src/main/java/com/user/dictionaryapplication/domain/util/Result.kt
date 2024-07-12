package com.user.dictionaryapplication.domain.util

sealed class Result<out T> {
    data class Loading<out T>(val data: T? = null) : Result<T>()
    data class Success<out T>(val data: T?) : Result<T>()
    data class Error<out T>(val data: T? = null, val message: String?) : Result<T>()
}
