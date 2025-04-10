package com.iraklyoda.userssocialapp.data.repository

import com.iraklyoda.userssocialapp.domain.common.Resource
import com.iraklyoda.userssocialapp.domain.model.LoginSession
import com.iraklyoda.userssocialapp.domain.repository.LogInRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLogInRepository : LogInRepository {

    private val loginSession = LoginSession(token = TOKEN_KEY)

    override suspend fun login(
        email: String,
        password: String,
        rememberMe: Boolean
    ): Flow<Resource<LoginSession>> {
        return flow {
            emit(
                Resource.Success(data = loginSession)
            )
        }
    }

    companion object {
        const val TOKEN_KEY = "123TestToken321"
    }
}