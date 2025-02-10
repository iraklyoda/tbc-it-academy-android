package com.example.baseproject.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

object ApiHelper {
    suspend fun <T> handleHttpRequest(
        apiCall: suspend () -> Response<T>
    ): Flow<Resource<T>> = flow {
        emit(Resource.Loading(true))

        try {
            val response = apiCall.invoke()

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(data = it))
                } ?: emit(Resource.Error(errorMessage = "Something is wrong"))
            } else {
                emit(Resource.Error(errorMessage = response.message()))
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    emit(Resource.Error(errorMessage = throwable.message ?: "IO"))
                }

                is HttpException -> {
                    emit(Resource.Error(errorMessage = throwable.message ?: "Http"))
                }

                is IllegalStateException -> {
                    emit(Resource.Error(errorMessage = throwable.message ?: "Illegal"))
                }

                else -> {
                    emit(Resource.Error(errorMessage = throwable.message ?: "Other"))
                }
            }
        } finally {
            emit(Resource.Loading(false))
        }

    }
}