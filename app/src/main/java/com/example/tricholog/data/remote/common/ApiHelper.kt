package com.example.tricholog.data.remote.common

import com.example.tricholog.domain.common.Resource
import com.example.tricholog.domain.error.ApiError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import android.util.Log

class ApiHelper @Inject constructor() {
    suspend fun <T> handleHttpRequest(
        apiCall: suspend () -> Response<T>
    ): Flow<Resource<T, ApiError>> = flow {
        emit(Resource.Loading)

        try {
            val response = apiCall.invoke()

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(data = it))
                } ?: run {
//                    Log.d("ApiError", "${response.errorBody()}")
                    emit(Resource.Error(ApiError.UnknownError))
                }
            } else {
//                Log.d("ApiError", "${response.errorBody()}")
                emit(Resource.Error(ApiError.UnknownError))
            }
        } catch (throwable: Throwable) {
//            Log.d("ApiError", "${throwable.message}")
            when (throwable) {
                is IOException -> {
                    emit(Resource.Error(error = ApiError.NetworkError))
                }

                is HttpException -> {
                    emit(Resource.Error(error = ApiError.HttpError(code = throwable.code())))
                }

                is IllegalStateException -> {
                    emit(Resource.Error(error = ApiError.UnknownError))
                }

                else -> {
                    emit(Resource.Error(error = ApiError.UnknownError))
                }
            }
        }
    }
}