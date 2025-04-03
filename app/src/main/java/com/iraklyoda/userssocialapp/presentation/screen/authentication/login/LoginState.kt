package com.iraklyoda.userssocialapp.presentation.screen.authentication.login

import com.iraklyoda.userssocialapp.domain.common.AuthFieldErrorType
import com.iraklyoda.userssocialapp.presentation.screen.authentication.login.model.LoginSessionUi

data class LoginState(
    val loader: Boolean = false,
    val apiError: String? = null,
    val loginSession: LoginSessionUi? = null,

    val emailError: AuthFieldErrorType? = null,
    val passwordError: AuthFieldErrorType? = null,
    val formBeenSubmitted: Boolean = false,
) {
    val isLoginBtnEnabled: Boolean
        get() = emailError == null && passwordError == null
}