package com.iraklyoda.userssocialapp.data

import com.iraklyoda.userssocialapp.data.local.db.UserEntity
import com.iraklyoda.userssocialapp.data.remote.dto.user.UserDto
import com.iraklyoda.userssocialapp.domain.model.User

object UserMapper {
    fun UserEntity.toUser(): User {
        return User(
            id = this.id,
            fullName = "${this.firstName} ${this.lastName}",
            avatarUrl = avatar,
            email = this.email
        )
    }

    fun UserDto.toEntity(): UserEntity {
        return UserEntity(
            id = id,
            email = email,
            firstName = firstName,
            lastName = this.lastName,
            avatar = this.avatar,
        )
    }
}

