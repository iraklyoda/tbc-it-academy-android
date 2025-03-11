package com.example.baseproject.ui.authentication.login

sealed class LoginFormEvent {
    data class EmailChanged(val email: String): LoginFormEvent()
    data class PasswordChanged(val password: String): LoginFormEvent()
    data class RememberMeChanged(val rememberMe: Boolean): LoginFormEvent()
    data class Submit(val rememberMe: Boolean): LoginFormEvent()
}