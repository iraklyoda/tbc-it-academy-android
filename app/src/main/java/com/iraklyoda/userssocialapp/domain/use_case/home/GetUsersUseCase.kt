package com.iraklyoda.userssocialapp.domain.use_case.home

import androidx.paging.PagingData
import com.iraklyoda.userssocialapp.data.repository.UserRepository
import com.iraklyoda.userssocialapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetUsersUseCase {
    operator fun invoke(): Flow<PagingData<User>>
}

class GetUsersUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): GetUsersUseCase {
    override fun invoke(): Flow<PagingData<User>> {
        return userRepository.getUsers()
    }
}