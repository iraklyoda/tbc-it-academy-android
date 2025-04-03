package com.iraklyoda.userssocialapp.presentation.screen.home.mapper

import com.iraklyoda.userssocialapp.domain.model.User
import com.iraklyoda.userssocialapp.presentation.screen.home.model.UserUi

fun User.toUi(): UserUi {
    return UserUi(id, fullName, email, avatarUrl)
}