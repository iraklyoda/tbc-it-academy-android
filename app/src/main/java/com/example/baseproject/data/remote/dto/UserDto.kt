package com.example.baseproject.data.remote.dto

import com.example.baseproject.presentation.home.UserUI
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDto(
    val page: Int,
    @SerialName("per_page")
    val perPage: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    val data: List<UserDto>
)

@Serializable
data class UserDto(
    val id: Int,
    val email: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    val avatar: String
)

fun UserDto.toUserUI(): UserUI {
    return UserUI(
        id = id,
        fullName = "$firstName $lastName",
        email = email,
        avatarUrl = avatar
    )
}