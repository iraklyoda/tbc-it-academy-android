package com.iraklyoda.userssocialapp.domain.use_case.auth

import com.iraklyoda.userssocialapp.domain.common.Resource
import com.iraklyoda.userssocialapp.domain.common.handleSuccess
import com.iraklyoda.userssocialapp.domain.model.LoginSession
import com.iraklyoda.userssocialapp.domain.preferences.AppPreferenceKeys
import com.iraklyoda.userssocialapp.domain.preferences.PreferencesStorage
import com.iraklyoda.userssocialapp.domain.repository.LogInRepository
import kotlinx.coroutines.flow.Flow
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