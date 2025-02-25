package com.example.tricholog.data.mapper

import com.example.tricholog.data.model.UserDto
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toUserDto(): UserDto{
    return UserDto(
        id = this.uid,
        email = this.email
    )
}