package com.iraklyoda.userssocialapp.presentation.screen.authentication.register.mapper

import com.iraklyoda.userssocialapp.domain.model.RegisterSession
import com.iraklyoda.userssocialapp.presentation.screen.authentication.register.model.RegisterSessionUi

fun RegisterSession.toUi(): RegisterSessionUi {
    return RegisterSessionUi(id, token)
}