package com.iraklyoda.userssocialapp.data.mapper

import com.iraklyoda.userssocialapp.data.local.db.UserEntity
import com.iraklyoda.userssocialapp.data.remote.dto.user.UserDto
import com.iraklyoda.userssocialapp.domain.model.GetUser

fun UserEntity.toDomain(): GetUser {
    return GetUser(
        id = id,
        fullName = "$firstName $lastName",
        avatarUrl = avatar,
        email = email
    )
}

fun UserDto.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        avatar = avatar,
    )
}


