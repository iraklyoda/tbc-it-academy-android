package com.example.baseproject.ui.authentication.register

sealed interface RegisterUiEvents {
    data class EmailChanged(val email: String): RegisterUiEvents
    data class PasswordChanged(val password: String): RegisterUiEvents
    data class RepeatedPasswordChanged(val repeatedPassword: String): RegisterUiEvents
    data object Submit: RegisterUiEvents
}