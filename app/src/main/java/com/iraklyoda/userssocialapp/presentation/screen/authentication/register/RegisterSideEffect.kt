package com.iraklyoda.userssocialapp.presentation.screen.authentication.register

sealed interface RegisterSideEffect {
    data class NavigateToLogin(val email: String, val password: String) : RegisterSideEffect
    data class ShowApiError(val message: String?) : RegisterSideEffect
}