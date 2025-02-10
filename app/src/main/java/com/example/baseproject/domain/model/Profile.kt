package com.example.baseproject.domain.model

import com.example.baseproject.data.remote.dto.ProfileDto

data class Profile(
    val email: String,
    val password: String
) {
    fun toDto(): ProfileDto {
        return ProfileDto(
            email = email,
            password = password
        )
    }
}