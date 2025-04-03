package com.iraklyoda.userssocialapp.domain.repository

import com.iraklyoda.userssocialapp.domain.common.Resource
import com.iraklyoda.userssocialapp.domain.model.LoginSession
import kotlinx.coroutines.flow.Flow

interface LogInRepository {
    suspend fun login(
        email: String,
        password: String,
        rememberMe: Boolean
    ): Flow<Resource<LoginSession>>
}