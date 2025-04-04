package com.iraklyoda.userssocialapp.presentation.screen.home.mapper

import com.iraklyoda.userssocialapp.domain.model.GetUser
import com.iraklyoda.userssocialapp.presentation.screen.home.model.UserUi

fun GetUser.toUi(): UserUi {
    return UserUi(id, fullName, email, avatarUrl)
}