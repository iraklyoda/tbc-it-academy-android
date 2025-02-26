package com.example.tricholog.ui.authentication.register

import com.example.tricholog.domain.error.AuthError

sealed class RegisterUiState {
    data object Idle: RegisterUiState()
    data object Loading: RegisterUiState()
    data class Success(val success: Boolean = true): RegisterUiState()
    data class Error(val error: AuthError): RegisterUiState()
}