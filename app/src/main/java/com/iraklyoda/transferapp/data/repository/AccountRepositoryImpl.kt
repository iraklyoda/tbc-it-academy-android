package com.iraklyoda.transferapp.data.repository

import com.iraklyoda.transferapp.data.mapper.toDomain
import com.iraklyoda.transferapp.data.remote.common.ApiHelper
import com.iraklyoda.transferapp.data.remote.service.AccountService
import com.iraklyoda.transferapp.domain.common.Resource
import com.iraklyoda.transferapp.domain.common.mapResource
import com.iraklyoda.transferapp.domain.model.GetDebitCard
import com.iraklyoda.transferapp.domain.repository.account.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val apiService: AccountService
): AccountRepository {
    override suspend fun getInternalAccounts(): Flow<Resource<List<GetDebitCard>>> {
        return apiHelper.handleHttpRequest {
            apiService.getAccounts()
        }.mapResource {
            it.map { debitCardDto -> debitCardDto.toDomain() }
        }
    }

    override suspend fun getReceiverAccount(identifier: String): Flow<Resource<GetDebitCard>> {
        return apiHelper.handleHttpRequest {
            apiService.getReceiverAccount(identifier = identifier)
        }.mapResource {
            it.toDomain()
        }
    }
}