package com.iraklyoda.userssocialapp.data.mapper

import com.iraklyoda.userssocialapp.data.remote.dto.RegisterResponseDto
import com.iraklyoda.userssocialapp.domain.model.RegisterSession

fun RegisterResponseDto.toRegisterSession(): RegisterSession {
    return RegisterSession(
        id = id,
        token = token
    )
}