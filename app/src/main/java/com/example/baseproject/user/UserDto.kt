package com.example.baseproject.user

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.util.UUID

@Serializable
data class UserDto(
    @Transient
    val username: String? = null,
    var email: String,
    val password: String
)