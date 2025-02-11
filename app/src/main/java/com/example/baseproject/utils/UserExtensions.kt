package com.example.baseproject.utils

import com.example.baseproject.data.local.db.UserEntity
import com.example.baseproject.data.remote.dto.UserDto
import com.example.baseproject.domain.model.User

fun UserDto.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        avatar = this.avatar,
    )
}

fun UserEntity.toUser(): User {
    return User(
        id = id,
        email = email,
        fullName = "$firstName  $lastName",
        avatarUrl = avatar
    )
}