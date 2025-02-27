package com.example.tricholog.domain.error

sealed class UserError {
    data object UserNotLoggedIn: UserError()
}