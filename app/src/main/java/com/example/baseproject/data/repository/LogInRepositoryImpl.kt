package com.example.baseproject.data.repository

import com.example.baseproject.data.remote.api.LoginService
import com.example.baseproject.data.remote.common.ApiHelper
import com.example.baseproject.data.remote.dto.ProfileDto
import com.example.baseproject.domain.common.Resource
import com.example.baseproject.domain.model.ProfileSession
import com.example.baseproject.domain.repository.LogInRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val loginService: LoginService
) : LogInRepository {
    override suspend fun login(
        email: String,
        password: String,
        rememberMe: Boolean
    ): Flow<Resource<ProfileSession>> {
        val profileDto: ProfileDto = ProfileDto(email = email, password = password)
        return apiHelper.handleHttpRequest { loginService.loginUser(profileDto) }
            .map { resource ->
                when (resource) {
                    is Resource.Loading -> Resource.Loading
                    is Resource.Error -> Resource.Error(resource.errorMessage)
                    is Resource.Success -> { Resource.Success(ProfileSession(email = email, token = resource.data.token)) }
                }
            }
    }
}