package com.roque.domain.util

sealed class Result<out T> {
    object Loading: Result<Nothing>()
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val type: Failure): Result<Nothing>()
}