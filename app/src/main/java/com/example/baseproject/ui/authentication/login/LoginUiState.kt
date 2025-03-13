package com.example.baseproject.ui.authentication.login

import com.example.baseproject.domain.common.AuthFieldErrorType
import com.example.baseproject.domain.model.LoginSession

data class LoginUiState(
    val email: String = "",
    val emailError: AuthFieldErrorType? = null,
    val password: String = "",
    val passwordError: AuthFieldErrorType? = null,
    val rememberMe: Boolean = false,
    val isLoading: Boolean = false,
    val formBeenSubmitted: Boolean = false,
    val loginSession: LoginSession? = null
) {
    val isLoginBtnEnabled: Boolean
        get() = emailError == null && passwordError == null
}