package com.example.baseproject.user

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    var id: Int? = null,
    val email: String,
    val password: String
)