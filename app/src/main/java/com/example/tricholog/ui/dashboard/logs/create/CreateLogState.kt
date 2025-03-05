package com.example.tricholog.ui.dashboard.logs.create

import com.example.tricholog.domain.error.ApiError

sealed class CreateLogState {
    data object Idle: CreateLogState()
    data object Loading:CreateLogState()
    data class Success(val success: Boolean = true): CreateLogState()
    data class Error(val error: ApiError): CreateLogState()
}