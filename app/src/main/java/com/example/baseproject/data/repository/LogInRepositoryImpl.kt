package com.example.baseproject.data.repository

import com.example.baseproject.data.local.datastore.DataStoreManager
import com.example.baseproject.data.local.datastore.DataStorePreferenceKeys
import com.example.baseproject.data.remote.api.LoginService
import com.example.baseproject.data.remote.common.ApiHelper
import com.example.baseproject.domain.common.Resource
import com.example.baseproject.data.remote.dto.ProfileDto
import com.example.baseproject.domain.model.ProfileSession
import com.example.baseproject.domain.repository.LogInRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val dataStoreManager: DataStoreManager,
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
                    is Resource.Success -> {
                        saveLoginPreferences(
                            email = email,
                            rememberMe = rememberMe,
                            token = resource.data.token
                        )
                        Resource.Success(ProfileSession(email = email, token = resource.data.token))
                    }
                }
            }
    }

    override suspend fun saveLoginPreferences(email: String, rememberMe: Boolean, token: String) {
        try {
            if (rememberMe) {
                dataStoreManager.saveValue(DataStorePreferenceKeys.TOKEN_KEY, token)
            }
            dataStoreManager.saveValue(DataStorePreferenceKeys.EMAIL_KEY, email)
        } catch (e: Exception) {
            throw RuntimeException("Failed to save login data: ${e.message}")
        }
    }
}