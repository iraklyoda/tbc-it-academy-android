package com.example.baseproject.domain.use_case.auth

import com.example.baseproject.domain.common.Resource
import com.example.baseproject.domain.model.RegisterSession
import com.example.baseproject.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SignUpUserUseCase {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<RegisterSession>>
}

class SignUpUserUseCaseImpl @Inject constructor(
    private val signUpRepository: SignUpRepository,
) : SignUpUserUseCase {
    override suspend operator fun invoke(email: String, password: String): Flow<Resource<RegisterSession>> {
        return signUpRepository.register(email, password)
    }
}