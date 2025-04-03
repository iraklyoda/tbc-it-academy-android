package com.iraklyoda.userssocialapp.presentation.screen.authentication.login.mapper

import com.iraklyoda.userssocialapp.domain.model.LoginSession
import com.iraklyoda.userssocialapp.presentation.screen.authentication.login.model.LoginSessionUi

fun LoginSession.toUi(): LoginSessionUi {
    return LoginSessionUi(token = token)
}