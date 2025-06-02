package com.roque.domain.util

sealed class ApiResponseResult<out T> {
    data class Success<out T>(val data: T): ApiResponseResult<T>()
    data class Error(val type: Failure): ApiResponseResult<Nothing>()
}