package com.iraklyoda.userssocialapp.presentation.screen.authentication.login

sealed interface LoginEvent {
    data class EmailChanged(val email: String) :
        LoginEvent

    data class PasswordChanged(val password: String) :
        LoginEvent

    data class RememberMeChanged(val rememberMe: Boolean) :
        LoginEvent

    data object RegisterBtnClicked : LoginEvent

    data class SetCredentials(val email: String, val password: String) : LoginEvent

    data object Submit :
        LoginEvent
}