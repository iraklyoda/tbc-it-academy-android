package com.iraklyoda.userssocialapp.presentation.screen.authentication.register

import com.iraklyoda.userssocialapp.domain.common.AuthFieldErrorType
import com.iraklyoda.userssocialapp.presentation.screen.authentication.register.model.RegisterSessionUi

data class RegisterState(
    val loader: Boolean = false,
    val apiError: String? = null,
    val registerSession: RegisterSessionUi? = null,

    val emailError: AuthFieldErrorType? = null,
    val passwordError: AuthFieldErrorType? = null,
    val repeatedPasswordError: AuthFieldErrorType? = null,
    val formBeenSubmitted: Boolean = false,
    ) {
    val isSignUpBtnEnabled: Boolean
        get() = emailError == null && passwordError == null && repeatedPasswordError == null
}
