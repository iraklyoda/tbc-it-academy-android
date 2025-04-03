package com.iraklyoda.userssocialapp.domain.use_case.validation

import com.iraklyoda.userssocialapp.domain.common.AuthFieldErrorType
import javax.inject.Inject

class ValidateRepeatedPasswordUseCase @Inject constructor() {
    operator fun invoke(password: String, repeatedPassword: String): AuthFieldErrorType? {
        if (repeatedPassword.isBlank())
            return AuthFieldErrorType.EMPTY

        if (password != repeatedPassword)
            return AuthFieldErrorType.PasswordsDoNotMatch

        return null
    }
}