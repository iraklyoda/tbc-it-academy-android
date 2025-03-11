package com.example.baseproject.domain.use_case.auth

import com.example.baseproject.domain.common.Resource
import com.example.baseproject.domain.model.ProfileSession
import com.example.baseproject.domain.repository.LogInRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogInUserUseCase @Inject constructor(
    private val loginRepository: LogInRepository
) {
    suspend operator fun invoke(email: String, password: String, rememberMe: Boolean): Flow<Resource<ProfileSession>> {
        return loginRepository.login(email, password, rememberMe)
    }
}