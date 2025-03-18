package com.example.baseproject.presentation.authentication.screen.register

import com.example.baseproject.domain.common.AuthFieldErrorType

sealed interface RegisterUiEvent {
    data class SetLoader(val isLoading: Boolean): RegisterUiEvent
    data class SetEmailInputError(val error: AuthFieldErrorType?): RegisterUiEvent
    data class SetPasswordInputError(val error: AuthFieldErrorType?): RegisterUiEvent
    data class SetRepeatedPasswordInputError(val error: AuthFieldErrorType?): RegisterUiEvent
    data class SetRegisterBtnStatus(val isEnabled: Boolean): RegisterUiEvent
    data class NavigateToLogin(val email: String, val password: String) : RegisterUiEvent
    data class ShowApiError(val message: String?) : RegisterUiEvent
}