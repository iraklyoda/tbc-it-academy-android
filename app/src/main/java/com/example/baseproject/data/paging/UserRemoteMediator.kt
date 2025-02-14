package com.example.baseproject.data.paging

import android.util.Log.d
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.baseproject.data.local.db.AppDatabase
import com.example.baseproject.data.local.db.UserEntity
import com.example.baseproject.data.remote.api.UserService
import com.example.baseproject.utils.toEntity
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator @Inject constructor(
    private val database: AppDatabase,
    private val userService: UserService
) : RemoteMediator<Int, UserEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    d("UserMediatorRefresh", "Refresh")
                    1
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    lastItem?.let {
                        val page = (lastItem.id / state.config.pageSize) + 1
                        d("UserMediatorItem", "$lastItem")
                        d("UserMediatorPage", page.toString())
                        page
                    } ?: 1
                }
            }

            val response = userService.getUsers(loadKey)
            if (!response.isSuccessful) throw IOException("HTTP ${response.code()}")

            val users = response.body()?.data ?: emptyList()


            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.userDao().clearAll()
                }
                val userEntities = users.map { it.toEntity() }
                database.userDao().upsertAll(userEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = users.isEmpty()
            )

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}