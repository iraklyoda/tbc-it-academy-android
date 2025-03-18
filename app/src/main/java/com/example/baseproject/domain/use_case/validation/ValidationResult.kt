package com.example.baseproject.domain.use_case.validation

import com.example.baseproject.domain.common.AuthFieldErrorType

sealed interface ValidationResult {
    data object Success : ValidationResult
    data class Error(val error: AuthFieldErrorType) : ValidationResult
}