package com.example.baseproject.presentation.authentication.screen.register

import com.example.baseproject.domain.common.AuthFieldErrorType

data class RegisterUiState(
    val emailError: AuthFieldErrorType? = null,
    val passwordError: AuthFieldErrorType? = null,
    val repeatedPasswordError: AuthFieldErrorType? = null,
    val formBeenSubmitted: Boolean = false,
) {
    val isSignUpBtnEnabled: Boolean
        get() = emailError == null && passwordError == null && repeatedPasswordError == null
}