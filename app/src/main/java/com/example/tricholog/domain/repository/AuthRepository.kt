package com.example.tricholog.domain.repository

import com.example.tricholog.domain.common.Resource
import com.example.tricholog.domain.error.AuthError
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Flow<Resource<Boolean, AuthError>>
    suspend fun signUp(email: String, password: String): Flow<Resource<Boolean, AuthError>>
    suspend fun logout()
}