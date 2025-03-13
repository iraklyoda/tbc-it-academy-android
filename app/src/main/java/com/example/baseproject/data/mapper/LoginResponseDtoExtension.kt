package com.example.baseproject.data.mapper

import com.example.baseproject.data.remote.dto.LoginResponseDto
import com.example.baseproject.domain.model.LoginSession

fun LoginResponseDto.toLoginSession(): LoginSession {
    return LoginSession(token = token)
}