package com.example.baseproject.ui.utils

import com.example.baseproject.R
import com.example.baseproject.domain.common.AuthFieldErrorType

object AuthFieldErrorMapper {
    fun mapToString(errorType: AuthFieldErrorType?): Int? {
        return when(errorType) {
            AuthFieldErrorType.EMPTY -> R.string.field_is_empty
            AuthFieldErrorType.TooShort -> R.string.too_short
            AuthFieldErrorType.InvalidFormat -> R.string.invalid_format
            AuthFieldErrorType.PasswordsDoNotMatch -> R.string.password_do_not_match
            null -> null
        }
    }
}