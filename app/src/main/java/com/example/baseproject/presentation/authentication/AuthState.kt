package com.example.baseproject.presentation.authentication

import com.example.baseproject.data.remote.dto.ProfileDto

data class AuthState(
    val loader: Boolean = false,
    val userInfo: ProfileDto? = null,
    val error: String? = null
)