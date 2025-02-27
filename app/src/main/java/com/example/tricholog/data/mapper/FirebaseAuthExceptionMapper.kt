package com.example.tricholog.data.mapper

import com.example.tricholog.domain.error.AuthError
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

object FirebaseAuthExceptionMapper {
    fun mapException(exception: Exception): AuthError {
        return when (exception) {
            is FirebaseAuthInvalidCredentialsException -> AuthError.InvalidCredentials
            is FirebaseNetworkException -> AuthError.NetworkError
            is FirebaseAuthUserCollisionException -> AuthError.EmailAlreadyExists
            else -> AuthError.UnknownError
        }
    }
}