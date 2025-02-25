package com.example.tricholog.data.repositories.auth

import com.example.tricholog.data.common.Resource
import com.example.tricholog.data.model.UserDto
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Flow<Resource<Boolean, Exception>>
    suspend fun signUp(email: String, password: String): Flow<Resource<Boolean, Exception>>
    suspend fun logout()
}