package com.example.tricholog.ui.authentication.login

sealed class LoginUiState {
    data object Idle: LoginUiState()
    data object Loading: LoginUiState()
    data class Success(val success: Boolean = true): LoginUiState()
    data class Error(val message: Exception): LoginUiState()
}