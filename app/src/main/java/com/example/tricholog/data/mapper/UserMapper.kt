package com.example.tricholog.data.mapper

import com.example.tricholog.data.model.UserDto
import com.example.tricholog.domain.model.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toUserDto(): UserDto{
    return UserDto(
        id = this.uid,
        email = this.email
    )
}

fun User.toFirestoreMap(): Map<String, Any> {
    return mapOf(
        "uid" to uid,
        "email" to email,
        "username" to username,
    )
}