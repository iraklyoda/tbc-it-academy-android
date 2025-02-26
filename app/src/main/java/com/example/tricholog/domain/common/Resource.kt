package com.example.tricholog.domain.common

sealed class Resource<out T, out E> {
    data class Success<out T>(val data: T) : Resource<T, Nothing>()
    data class Error<out E>(val error: E) : Resource<Nothing, E>()
    data object Loading : Resource<Nothing, Nothing>()
}