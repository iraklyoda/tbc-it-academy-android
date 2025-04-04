package com.iraklyoda.userssocialapp.presentation.screen.authentication.mapper

import com.iraklyoda.userssocialapp.R
import com.iraklyoda.userssocialapp.domain.common.AuthFieldErrorType

fun AuthFieldErrorType.mapToStringResource(): Int {
    return when (this) {
        AuthFieldErrorType.EMPTY -> R.string.field_is_empty
        AuthFieldErrorType.TooShort -> R.string.too_short
        AuthFieldErrorType.InvalidFormat -> R.string.invalid_format
        AuthFieldErrorType.PasswordsDoNotMatch -> R.string.password_do_not_match
    }
}