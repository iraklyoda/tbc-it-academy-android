package com.iraklyoda.userssocialapp.domain.repository

import com.iraklyoda.userssocialapp.domain.common.Resource
import com.iraklyoda.userssocialapp.domain.model.RegisterSession
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {
    suspend fun register(email: String, password: String): Flow<Resource<RegisterSession>>
}