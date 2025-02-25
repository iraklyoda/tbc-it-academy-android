package com.example.tricholog.ui.authentication.register

sealed class RegisterUiState {
    data object Idle: RegisterUiState()
    data object Loading: RegisterUiState()
    data class Success(val success: Boolean = true): RegisterUiState()
    data class Error(val message: Exception): RegisterUiState()
}