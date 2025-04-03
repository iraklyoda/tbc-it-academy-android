package com.iraklyoda.userssocialapp.presentation.screen.authentication.register

sealed interface RegisterEvent {
    data class EmailChanged(val email: String): RegisterEvent
    data class PasswordChanged(val password: String, val repeatedPassword: String): RegisterEvent
    data class RepeatedPasswordChanged(val repeatedPassword: String, val password: String):
        RegisterEvent

    data object Submit: RegisterEvent
}