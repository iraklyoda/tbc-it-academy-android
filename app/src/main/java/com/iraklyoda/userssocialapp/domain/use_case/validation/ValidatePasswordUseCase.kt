package com.iraklyoda.userssocialapp.domain.use_case.validation

import com.iraklyoda.userssocialapp.domain.common.AuthFieldErrorType
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