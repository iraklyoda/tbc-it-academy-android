package com.iraklyoda.userssocialapp.presentation.screen.authentication.login

data class LoginState(
    val loader: Boolean = false,
    val apiError: String? = null,
    val emailErrorResource: Int? = null,
    val passwordErrorResource: Int? = null,
    val formBeenSubmitted: Boolean = false,
) {
    val isLoginBtnEnabled: Boolean
        get() = emailErrorResource == null && passwordErrorResource == null
}