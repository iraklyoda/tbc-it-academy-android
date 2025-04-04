package com.iraklyoda.userssocialapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.iraklyoda.userssocialapp.data.local.db.AppDatabase
import com.iraklyoda.userssocialapp.data.mapper.toDomain
import com.iraklyoda.userssocialapp.data.paging.UserRemoteMediator
import com.iraklyoda.userssocialapp.domain.model.GetUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val database: AppDatabase,
    private val userRemoteMediator: UserRemoteMediator,
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getUsers(): Flow<PagingData<GetUser>> {
        return Pager(
            config = PagingConfig(
                pageSize = 6,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            remoteMediator = userRemoteMediator,
            pagingSourceFactory = { database.userDao().pagingSource() }
        ).flow.map { entity ->
            entity.map { it.toDomain() }
        }
    }
}