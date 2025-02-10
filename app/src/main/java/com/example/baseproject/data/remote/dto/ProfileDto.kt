package com.example.baseproject.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class ProfileDto(
    var email: String,
    val password: String
)