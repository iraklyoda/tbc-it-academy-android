package com.example.baseproject.user

data class AuthState(
    val loader: Boolean = false,
    val userInfo: ProfileDto? = null,
    val error: String? = null
)