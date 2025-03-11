package com.example.baseproject.domain.use_case.validation

import com.example.baseproject.domain.common.AuthFieldErrorType

data class ValidationResult(
    val successful: Boolean = false,
    val error: AuthFieldErrorType? = null
)
