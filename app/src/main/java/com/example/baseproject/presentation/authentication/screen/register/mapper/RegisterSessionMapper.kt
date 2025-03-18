package com.example.baseproject.presentation.authentication.screen.register.mapper

import com.example.baseproject.domain.model.RegisterSession
import com.example.baseproject.presentation.authentication.screen.register.model.RegisterSessionUi

fun RegisterSession.toUi(): RegisterSessionUi {
    return RegisterSessionUi(id, token)
}