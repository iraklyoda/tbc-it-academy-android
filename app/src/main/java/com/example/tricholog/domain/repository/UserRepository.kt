package com.example.tricholog.domain.repository

import com.example.tricholog.domain.model.User

interface UserRepository {
    suspend fun saveUser(user: User): Boolean
    suspend fun getUser(): User?
}