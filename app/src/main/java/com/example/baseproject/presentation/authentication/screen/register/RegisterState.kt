package com.example.baseproject.presentation.authentication.screen.register

import com.example.baseproject.presentation.authentication.screen.register.model.RegisterSessionUi

data class RegisterState(
    var loader: Boolean = false,
    var error: String? = null,
    var registerSession: RegisterSessionUi? = null
)
