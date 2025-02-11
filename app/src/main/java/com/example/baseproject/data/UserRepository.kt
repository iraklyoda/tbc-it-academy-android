package com.example.baseproject.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.baseproject.data.local.db.AppDatabase
import com.example.baseproject.data.paging.UserRemoteMediator
import com.example.baseproject.data.remote.api.UserService
import com.example.baseproject.domain.model.User
import com.example.baseproject.utils.toUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_PER_PAGE = 6

class UserRepository(
    private val database: AppDatabase,
    private val networkService: UserService
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getUsers(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = USER_PER_PAGE,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            remoteMediator = UserRemoteMediator(database = database, networkService = networkService),
            pagingSourceFactory = { database.userDao().pagingSource() }
        ).flow.map { entity ->
            entity.map { it.toUser() }
        }
    }
}