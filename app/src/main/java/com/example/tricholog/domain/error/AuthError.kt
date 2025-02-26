package com.example.tricholog.domain.error

sealed class AuthError {
    data object NetworkError: AuthError()
    data object EmailAlreadyExists: AuthError()
    data object InvalidCredentials: AuthError()
    data object UnknownError: AuthError()
}