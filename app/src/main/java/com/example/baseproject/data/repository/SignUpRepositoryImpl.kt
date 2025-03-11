package com.example.baseproject.data.repository

import com.example.baseproject.data.remote.api.RegisterService
import com.example.baseproject.data.remote.common.ApiHelper
import com.example.baseproject.domain.common.Resource
import com.example.baseproject.data.remote.dto.ProfileDto
import com.example.baseproject.domain.model.RegisterSession
import com.example.baseproject.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val registerService: RegisterService
): SignUpRepository {
    override suspend fun register(email: String, password: String): Flow<Resource<RegisterSession>> {
        val profileDto: ProfileDto = ProfileDto(email, password)
        return apiHelper.handleHttpRequest {
            registerService.registerUser(profileDto)
        }.map { resource ->
            when (resource) {
                is Resource.Success -> Resource.Success(RegisterSession(email = email, password = password))

                is Resource.Error -> Resource.Error(errorMessage = resource.errorMessage)

                is Resource.Loading -> Resource.Loading
            }
        }
    }

}