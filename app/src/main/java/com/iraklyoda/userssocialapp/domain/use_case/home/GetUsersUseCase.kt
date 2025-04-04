package com.iraklyoda.userssocialapp.domain.use_case.home

import androidx.paging.PagingData
import com.iraklyoda.userssocialapp.data.repository.UserRepository
import com.iraklyoda.userssocialapp.domain.model.GetUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetUsersUseCase {
    operator fun invoke(): Flow<PagingData<GetUser>>
}

class GetUsersUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): GetUsersUseCase {
    override fun invoke(): Flow<PagingData<GetUser>> {
        return userRepository.getUsers()
    }
}