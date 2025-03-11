package com.example.baseproject.domain.use_case.validation

import com.example.baseproject.domain.common.AuthFieldErrorType
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(error = AuthFieldErrorType.EMPTY)
        }

        if (password.length < 8) {
            return ValidationResult(error = AuthFieldErrorType.TooShort)
        }
        return ValidationResult(
            successful = true
        )
    }
}