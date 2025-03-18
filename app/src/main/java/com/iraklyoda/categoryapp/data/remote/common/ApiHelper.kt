package com.iraklyoda.categoryapp.data.remote.common

import com.iraklyoda.categoryapp.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ApiHelper {
    suspend fun <T> handleHttpRequest(
        apiCall: suspend () -> Response<T>
    ): Flow<Resource<T>> = flow {
        emit(Resource.Loading(loading = true))
        try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(data = it))
                } ?: emit(Resource.Error(errorMessage = "Something is wrong"))
            } else {
                val errorMessage = response.message().ifEmpty { "error code: ${response.code()}" }
                emit(Resource.Error(errorMessage = errorMessage))
            }
            emit(Resource.Loading(loading = false))
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> emit(Resource.Error(errorMessage = throwable.message ?: "IO"))
                is HttpException -> emit(Resource.Error(errorMessage = throwable.message ?: "Http"))
                is IllegalStateException -> emit(
                    Resource.Error(
                        throwable.message ?: "IllegalState"
                    )
                )
                else -> emit(Resource.Error(errorMessage = throwable.message ?: "Other"))
            }
            emit(Resource.Loading(loading = false))
        }
    }
}