package com.example.baseproject.domain.use_case.validation

import com.example.baseproject.domain.common.AuthFieldErrorType
import javax.inject.Inject

class ValidateRepeatedPasswordUseCase @Inject constructor() {
    operator fun invoke(password: String, repeatedPassword: String): ValidationResult {
        if (repeatedPassword.isBlank())
            return ValidationResult(error = AuthFieldErrorType.EMPTY)

        if (password != repeatedPassword)
            return ValidationResult(error = AuthFieldErrorType.PasswordsDoNotMatch)

        return ValidationResult(successful = true)
    }
}