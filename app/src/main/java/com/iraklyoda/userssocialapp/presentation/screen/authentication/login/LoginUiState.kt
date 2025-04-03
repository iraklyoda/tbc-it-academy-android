package com.iraklyoda.userssocialapp.presentation.screen.authentication.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
)