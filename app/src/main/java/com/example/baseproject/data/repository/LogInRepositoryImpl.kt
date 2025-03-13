package com.example.baseproject.data.repository

import com.example.baseproject.data.mapper.toLoginSession
import com.example.baseproject.data.remote.api.LoginService
import com.example.baseproject.data.remote.common.ApiHelper
import com.example.baseproject.domain.common.Resource
import com.example.baseproject.domain.common.mapResource
import com.example.baseproject.domain.model.LoginSession
import com.example.baseproject.domain.repository.LogInRepository
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