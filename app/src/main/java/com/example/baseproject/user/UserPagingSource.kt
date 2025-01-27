package com.example.baseproject.user

import android.util.Log.d
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.baseproject.client.UserService
import kotlinx.coroutines.delay

class UserPagingSource(
    private val backend: UserService
) : PagingSource<Int, UserDto>() {
    override fun getRefreshKey(state: PagingState<Int, UserDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserDto> {
        return try {
            val page = params.key ?: 1
            val response = backend.getUsersData(page)
            if (response.isSuccessful) {
                val users: List<UserDto> = response.body()?.data ?: listOf()
                val totalPages = response.body()?.totalPages ?: 1
                d("UserPagingSource", "Current page: $page, Total pages: $totalPages, Users count: ${users.size}")
                LoadResult.Page(
                    data = users,
                    prevKey = if (page > 1) page - 1 else null,
                    nextKey = if (page < totalPages) page + 1 else null
                )
            } else {
                LoadResult.Error(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}