package com.example.baseproject.presentation.authentication.screen.register

sealed interface RegisterEvent {
    data class EmailChanged(val email: String): RegisterEvent
    data class PasswordChanged(val password: String, val repeatedPassword: String): RegisterEvent
    data class RepeatedPasswordChanged(val repeatedPassword: String, val password: String): RegisterEvent
    data class Submit(val email: String, val password: String, val repeatedPassword: String): RegisterEvent
}