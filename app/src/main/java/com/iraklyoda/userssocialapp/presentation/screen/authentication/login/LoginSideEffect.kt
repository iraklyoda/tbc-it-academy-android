package com.iraklyoda.userssocialapp.presentation.screen.authentication.login

sealed interface LoginSideEffect {
    data object NavigateToRegister : LoginSideEffect
    data object NavigateToHome : LoginSideEffect
    data class ShowAuthError(val message: String?) : LoginSideEffect
}