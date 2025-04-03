package com.iraklyoda.userssocialapp.domain.use_case.validation

import com.iraklyoda.userssocialapp.domain.common.AuthFieldErrorType

sealed interface ValidationResult {
    data object Success : ValidationResult
    data class Error(val error: AuthFieldErrorType) : ValidationResult
}