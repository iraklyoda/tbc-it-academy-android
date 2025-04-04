package com.iraklyoda.userssocialapp.presentation.screen.authentication.register

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val repeatedPassword: String = "",
    val repeatedPasswordVisible: Boolean = false,
)