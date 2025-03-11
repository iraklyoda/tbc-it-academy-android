package com.example.baseproject.domain.repository

import com.example.baseproject.domain.common.Resource
import com.example.baseproject.domain.model.ProfileSession
import kotlinx.coroutines.flow.Flow

interface LogInRepository {
    suspend fun login(
        email: String,
        password: String,
        rememberMe: Boolean
    ): Flow<Resource<ProfileSession>>
}