package com.example.baseproject.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.baseproject.common.ApiHelper
import com.example.baseproject.common.Resource
import com.example.baseproject.data.remote.api.UserService
import com.example.baseproject.data.remote.dto.UserDto

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
            val result = ApiHelper.handleHttpRequest {   backend.getUsersData(page) }

            when (result) {
                is Resource.Success -> {
                    val users: List<UserDto> = result.data.data
                    val totalPages = result.data.totalPages
                    LoadResult.Page(
                        data = users,
                        prevKey = null,
                        nextKey = if (page < totalPages) page + 1 else null
                    )
                }
                is Resource.Error -> {
                    LoadResult.Error(Exception("Error: ${result.errorMessage}"))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}