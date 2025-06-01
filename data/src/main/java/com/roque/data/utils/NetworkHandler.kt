package com.roque.data.utils

import com.roque.domain.util.Failure
import retrofit2.Response
import com.roque.domain.util.Result
import java.io.IOException

class NetworkHandler {
    fun <T> handleResponse(response: Response<T>): Result<T> {
        return if (response.isSuccessful) {
            response.body()?.let { Result.Success(it) } ?: Result.Error(Failure.UnknownError("Body null"))
        } else {
            Result.Error(Failure.ServerError)
        }
    }

    suspend fun <T> safeCall(apiCall: suspend () -> Response<T>): Result<T> = try {
        handleResponse(apiCall())
    } catch (e: IOException) {
        Result.Error(Failure.NetworkError)
    } catch (e: Exception) {
        Result.Error(Failure.UnknownError(e.message ?: "Unknown Error"))
    }
}
