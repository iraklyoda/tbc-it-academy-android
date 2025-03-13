package com.example.baseproject.data.repository

import com.example.baseproject.data.mapper.toRegisterSession
import com.example.baseproject.data.remote.api.RegisterService
import com.example.baseproject.data.remote.common.ApiHelper
import com.example.baseproject.domain.common.Resource
import com.example.baseproject.domain.common.mapResource
import com.example.baseproject.domain.model.RegisterSession
import com.example.baseproject.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val registerService: RegisterService
): SignUpRepository {
    override suspend fun register(email: String, password: String): Flow<Resource<RegisterSession>> {
        return apiHelper.handleHttpRequest {
            registerService.registerUser(email = email, password = password)
        }.mapResource {
            it.toRegisterSession()
        }
    }
}