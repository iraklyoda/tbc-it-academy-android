package com.iraklyoda.userssocialapp.data.mapper

import com.iraklyoda.userssocialapp.data.remote.dto.LoginResponseDto
import com.iraklyoda.userssocialapp.domain.model.LoginSession

fun LoginResponseDto.toLoginSession(): LoginSession {
    return LoginSession(token = token)
}