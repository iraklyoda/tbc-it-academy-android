package com.example.tricholog.domain.error

sealed class ApiError {
    data class HttpError(val code: Int) : ApiError()
    data object NetworkError : ApiError()
    data object UnknownError : ApiError()
    data object UnauthorizedError : ApiError()
}