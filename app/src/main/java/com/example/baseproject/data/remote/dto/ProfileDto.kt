package com.example.baseproject.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class ProfileDto(
    @Transient
    val username: String? = null,
    var email: String,
    val password: String
)