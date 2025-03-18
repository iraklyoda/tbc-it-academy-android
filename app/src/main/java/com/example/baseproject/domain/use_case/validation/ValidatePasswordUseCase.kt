package com.example.baseproject.domain.use_case.validation

import com.example.baseproject.domain.common.AuthFieldErrorType
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): AuthFieldErrorType? {
        if (password.isBlank()) {
            return AuthFieldErrorType.EMPTY
        }

        if (password.length < 8) {
            return AuthFieldErrorType.TooShort
        }
        return null
    }
}