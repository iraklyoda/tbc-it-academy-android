package com.example.baseproject.domain.repository

import com.example.baseproject.domain.common.Resource
import com.example.baseproject.domain.model.RegisterSession
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {
    suspend fun register(email: String, password: String): Flow<Resource<RegisterSession>>
}