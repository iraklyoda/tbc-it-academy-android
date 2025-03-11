package com.example.baseproject.ui.authentication.login

import com.example.baseproject.domain.common.AuthFieldErrorType

data class LoginFormState(
    val email: String = "",
    val emailError: AuthFieldErrorType? = null,
    val password: String = "",
    val passwordError: AuthFieldErrorType? = null,
    val rememberMe: Boolean = false
)