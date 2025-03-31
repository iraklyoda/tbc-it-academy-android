package com.iraklyoda.transferapp.domain.use_case.account

import com.iraklyoda.transferapp.domain.common.Resource
import com.iraklyoda.transferapp.domain.model.GetDebitCard
import com.iraklyoda.transferapp.domain.repository.account.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetReceiverAccountUseCase {
    suspend operator fun invoke(identifier: String): Flow<Resource<GetDebitCard>>
}

class GetReceiverAccountUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository
): GetReceiverAccountUseCase {

    override suspend fun invoke(identifier: String): Flow<Resource<GetDebitCard>> {
        return accountRepository.getReceiverAccount(identifier = identifier)
    }
}