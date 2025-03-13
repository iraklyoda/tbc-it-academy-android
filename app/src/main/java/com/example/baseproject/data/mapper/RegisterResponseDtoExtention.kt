package com.example.baseproject.data.mapper

import com.example.baseproject.data.remote.dto.RegisterResponseDto
import com.example.baseproject.domain.model.RegisterSession

fun RegisterResponseDto.toRegisterSession(): RegisterSession {
    return RegisterSession(
        id = id,
        token = token
    )
}