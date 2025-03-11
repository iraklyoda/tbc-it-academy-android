package com.example.baseproject.data

import com.example.baseproject.data.local.db.UserEntity
import com.example.baseproject.data.remote.dto.user.UserDto
import com.example.baseproject.domain.model.User

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
            id = this.id,
            email = this.email,
            firstName = this.firstName,
            lastName = this.lastName,
            avatar = this.avatar,
        )
    }
}

