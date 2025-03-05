package com.example.tricholog.domain.repository

import com.example.tricholog.domain.common.Resource
import com.example.tricholog.domain.error.ApiError
import com.example.tricholog.domain.error.AuthError
import com.example.tricholog.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Flow<Resource<Boolean, AuthError>>
    suspend fun signUp(email: String, username: String, password: String): Flow<Resource<Boolean, AuthError>>
    suspend fun logout()
}