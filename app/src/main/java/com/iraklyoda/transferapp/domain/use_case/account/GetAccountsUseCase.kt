package com.iraklyoda.transferapp.domain.use_case.account

import com.iraklyoda.transferapp.domain.common.Resource
import com.iraklyoda.transferapp.domain.model.GetDebitCard
import com.iraklyoda.transferapp.domain.repository.account.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetAccountsUseCase {
    suspend operator fun invoke(): Flow<Resource<List<GetDebitCard>>>
}

class GetAccountsUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository
) : GetAccountsUseCase {
    override suspend fun invoke(): Flow<Resource<List<GetDebitCard>>> {
        return accountRepository.getInternalAccounts()
    }
}