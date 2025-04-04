package com.iraklyoda.userssocialapp.presentation.screen.authentication.register

data class RegisterState(
    val loader: Boolean = false,
    val apiError: String? = null,

    val emailErrorResource: Int? = null,
    val passwordErrorResource: Int? = null,
    val repeatedPasswordErrorResource: Int? = null,
    val formBeenSubmitted: Boolean = false,
) {
    val isSignUpBtnEnabled: Boolean
        get() = emailErrorResource == null && passwordErrorResource == null && repeatedPasswordErrorResource == null
}
