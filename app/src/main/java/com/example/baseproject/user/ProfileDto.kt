package com.example.baseproject.user

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class ProfileDto(
    @Transient
    val username: String? = null,
    var email: String,
    val password: String
)