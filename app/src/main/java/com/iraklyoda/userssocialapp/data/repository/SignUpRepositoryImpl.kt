package com.iraklyoda.userssocialapp.data.repository

import com.iraklyoda.userssocialapp.data.mapper.toRegisterSession
import com.iraklyoda.userssocialapp.data.remote.api.RegisterService
import com.iraklyoda.userssocialapp.data.remote.common.ApiHelper
import com.iraklyoda.userssocialapp.domain.common.Resource
import com.iraklyoda.userssocialapp.domain.common.mapResource
import com.iraklyoda.userssocialapp.domain.model.RegisterSession
import com.iraklyoda.userssocialapp.domain.repository.SignUpRepository
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