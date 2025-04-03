package com.iraklyoda.userssocialapp.domain.use_case.validation

import com.iraklyoda.userssocialapp.domain.common.AuthFieldErrorType
import java.util.regex.Pattern.compile
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {
    operator fun invoke(email: String): AuthFieldErrorType? {
        if (email.isBlank()) {
            return AuthFieldErrorType.EMPTY
        }

        if (!emailRegex.matcher(email).matches()) {
            return AuthFieldErrorType.InvalidFormat
        }

        return null
    }

    private val emailRegex = compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
}