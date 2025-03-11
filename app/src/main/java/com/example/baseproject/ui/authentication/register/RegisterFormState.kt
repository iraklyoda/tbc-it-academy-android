package com.example.baseproject.ui.authentication.register

import com.example.baseproject.domain.common.AuthFieldErrorType

data class RegisterFormState(
    val email: String = "",
    val emailError: AuthFieldErrorType? = null,
    val password: String = "",
    val passwordError: AuthFieldErrorType? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: AuthFieldErrorType? = null
)