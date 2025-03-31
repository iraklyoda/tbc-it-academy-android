package com.iraklyoda.transferapp.domain.repository.account

import com.iraklyoda.transferapp.domain.common.Resource
import com.iraklyoda.transferapp.domain.model.GetDebitCard
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun getInternalAccounts(): Flow<Resource<List<GetDebitCard>>>
    suspend fun getReceiverAccount(identifier: String): Flow<Resource<GetDebitCard>>
}