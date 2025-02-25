package com.example.tricholog.ui.mapper

import com.example.tricholog.data.model.UserDto
import com.example.tricholog.ui.authentication.model.User

fun UserDto.toUserUI(): User {
    return User(
        id = this.id,
        email = this.email
    )
}