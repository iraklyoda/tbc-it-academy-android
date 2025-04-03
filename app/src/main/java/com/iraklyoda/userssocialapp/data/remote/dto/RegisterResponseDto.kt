package com.iraklyoda.userssocialapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseDto(
    val id: Int,
    val token: String
)