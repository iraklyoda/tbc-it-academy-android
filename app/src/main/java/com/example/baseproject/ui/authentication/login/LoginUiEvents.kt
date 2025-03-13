package com.example.baseproject.ui.authentication.login

sealed interface LoginUiEvents {
    data class EmailChanged(val email: String): LoginUiEvents
    data class PasswordChanged(val password: String): LoginUiEvents
    data class RememberMeChanged(val rememberMe: Boolean): LoginUiEvents
    data object Submit : LoginUiEvents
}