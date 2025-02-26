package com.example.tricholog.ui.authentication.login

import com.example.tricholog.domain.error.AuthError

sealed class LoginUiState {
    data object Idle: LoginUiState()
    data object Loading: LoginUiState()
    data class Success(val success: Boolean = true): LoginUiState()
    data class Error(val error: AuthError): LoginUiState()
}