package com.example.baseproject.ui.authentication.register

import com.example.baseproject.domain.common.AuthFieldErrorType
import com.example.baseproject.domain.model.RegisterSession

data class RegisterUiState(
    val email: String = "",
    val emailError: AuthFieldErrorType? = null,
    val password: String = "",
    val passwordError: AuthFieldErrorType? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: AuthFieldErrorType? = null,
    val isLoading: Boolean = false,
    val formBeenSubmitted: Boolean = false,
    val registerSession: RegisterSession? = null
) {
    val isSignUpBtnEnabled: Boolean
        get() = emailError == null && passwordError == null && repeatedPasswordError == null
}