package com.example.baseproject.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    var email: String,
    val password: String
)