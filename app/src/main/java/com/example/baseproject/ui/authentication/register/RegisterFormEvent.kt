package com.example.baseproject.ui.authentication.register

sealed class RegisterFormEvent {
    data class EmailChanged(val email: String): RegisterFormEvent()
    data class PasswordChanged(val password: String): RegisterFormEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String): RegisterFormEvent()
    data object Submit: RegisterFormEvent()
}