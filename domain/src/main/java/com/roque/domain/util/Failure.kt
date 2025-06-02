package com.roque.domain.util

sealed class Failure {
    object NetworkError : Failure()
    object ServerError : Failure()
    data class UnknownError(val error: String) : Failure()
}