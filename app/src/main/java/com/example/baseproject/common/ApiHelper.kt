package com.example.baseproject.common

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

object ApiHelper {
    suspend fun <T> handleHttpRequest(
        apiCall: suspend () -> Response<T>
    ): Resource<T> {
        return try {
            val response = apiCall.invoke()

            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(data = it)
                } ?: Resource.Error(errorMessage = "Something iss wrong")
            } else {
                Resource.Error(errorMessage = response.message())
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    Resource.Error(errorMessage = throwable.message ?: "IO")
                }

                is HttpException -> {
                    Resource.Error(errorMessage = throwable.message ?: "Http")
                }

                is IllegalStateException -> {
                    Resource.Error(errorMessage = throwable.message ?: "Illegal")
                }

                else -> {
                    Resource.Error(errorMessage = throwable.message ?: "Other")
                }
            }
        }
    }
}