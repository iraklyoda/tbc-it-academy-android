package com.example.baseproject.domain.use_case.auth

import com.example.baseproject.domain.common.Resource
import com.example.baseproject.domain.common.handleSuccess
import com.example.baseproject.domain.model.LoginSession
import com.example.baseproject.domain.preferences.AppPreferenceKeys
import com.example.baseproject.domain.preferences.PreferencesStorage
import com.example.baseproject.domain.repository.LogInRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface LogInUserUseCase {
    suspend operator fun invoke(
        email: String,
        password: String,
        rememberMe: Boolean
    ): Flow<Resource<LoginSession>>
}

class LogInUserUseCaseImpl @Inject constructor(
    private val loginRepository: LogInRepository,
    private val preferencesStorage: PreferencesStorage
) : LogInUserUseCase {
    override suspend operator fun invoke(
        email: String,
        password: String,
        rememberMe: Boolean
    ): Flow<Resource<LoginSession>> {
        return loginRepository.login(email, password, rememberMe)
            .handleSuccess { resource ->
                saveLoginPreferences(email = email, token = resource.token, rememberMe = rememberMe)
            }
    }

    private suspend fun saveLoginPreferences(email: String, token: String, rememberMe: Boolean) {
        preferencesStorage.saveValue(key = AppPreferenceKeys.EMAIL_KEY, value = email)
        if (rememberMe) {
            preferencesStorage.saveValue(key = AppPreferenceKeys.TOKEN_KEY, value = token)
        }
    }
}