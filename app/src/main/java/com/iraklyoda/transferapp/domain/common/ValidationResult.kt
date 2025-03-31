package com.iraklyoda.transferapp.domain.common

sealed class ValidationResult {
    data object Success : ValidationResult()
    data class Error(val errorMessage: String) : ValidationResult()
}