package com.example.baseproject.requests

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String
)
