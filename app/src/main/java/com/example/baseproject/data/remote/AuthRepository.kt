package com.example.baseproject.data.remote

import com.example.baseproject.common.ApiHelper
import com.example.baseproject.common.Resource
import com.example.baseproject.data.local.AuthPreferencesRepository
import com.example.baseproject.data.remote.api.AuthService
import com.example.baseproject.data.remote.dto.ProfileDto
import com.example.baseproject.domain.model.Profile
import com.example.baseproject.domain.model.ProfileSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val authPreferencesRepository: AuthPreferencesRepository,
    private val authService: AuthService
) {
    suspend fun login(
        email: String,
        password: String,
        rememberMe: Boolean
    ): Flow<Resource<ProfileSession>> {
        val profileDto: ProfileDto = Profile(email = email, password = password).toDto()
        return apiHelper.handleHttpRequest {
            authService.loginUser(profileDto)
        }.map { resource ->
            when (resource) {
                is Resource.Success -> {
                    val response = resource.data
                    val profileSession =
                        ProfileSession(token = response.token, email = profileDto.email)

                    authPreferencesRepository.saveEmail(email = profileSession.email)
                    if (rememberMe) {
                        authPreferencesRepository.saveToken(profileSession.token)
                    }
                    Resource.Success(profileSession)
                }

                is Resource.Error -> Resource.Error(resource.errorMessage)
                is Resource.Loading -> Resource.Loading(resource.loading)
            }

        }
    }

    suspend fun register(email: String, password: String): Flow<Resource<Profile>> {
        val profileDto: ProfileDto = Profile(email = email, password = password).toDto()
        return apiHelper.handleHttpRequest {
            authService.registerUser(profileDto)
        }.map { resource ->
            when (resource) {
                is Resource.Success -> {
                    Resource.Success(Profile(email = email, password = password))
                }
                is Resource.Error -> {
                    Resource.Error(errorMessage = resource.errorMessage)
                }
                is Resource.Loading -> {
                    Resource.Loading(loading = resource.loading)
                }
            }
        }
    }

}