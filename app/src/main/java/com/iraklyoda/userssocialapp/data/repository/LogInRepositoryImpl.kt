package com.iraklyoda.userssocialapp.data.repository

import com.iraklyoda.userssocialapp.data.mapper.toLoginSession
import com.iraklyoda.userssocialapp.data.remote.api.LoginService
import com.iraklyoda.userssocialapp.data.remote.common.ApiHelper
import com.iraklyoda.userssocialapp.domain.common.Resource
import com.iraklyoda.userssocialapp.domain.common.mapResource
import com.iraklyoda.userssocialapp.domain.model.LoginSession
import com.iraklyoda.userssocialapp.domain.repository.LogInRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val loginService: LoginService
) : LogInRepository {
    override suspend fun login(
        email: String,
        password: String,
        rememberMe: Boolean
    ): Flow<Resource<LoginSession>> {
        return apiHelper.handleHttpRequest { loginService.loginUser(email = email, password = password) }
            .mapResource {
                it.toLoginSession()
            }
    }
}