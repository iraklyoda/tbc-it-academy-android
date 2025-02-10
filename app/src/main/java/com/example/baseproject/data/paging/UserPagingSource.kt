package com.example.baseproject.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.baseproject.common.ApiHelper
import com.example.baseproject.common.Resource
import com.example.baseproject.data.remote.api.UserService
import com.example.baseproject.data.remote.dto.UserDto
import com.example.baseproject.data.remote.dto.toUserUI
import com.example.baseproject.presentation.home.UserUI
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.single
import retrofit2.HttpException

class UserPagingSource(
    private val apiService: UserService
) : PagingSource<Int, UserUI>() {

    override fun getRefreshKey(state: PagingState<Int, UserUI>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserUI> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getUsersData(page)

            if (response.isSuccessful) {
                response.body()?.let { data ->
                    val usersUI: List<UserUI> = data.data.map { it.toUserUI() }
                    val totalPages = data.totalPages
                    LoadResult.Page(
                        data = usersUI,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (page < totalPages) page + 1 else null
                    )
                } ?: LoadResult.Error(NullPointerException("Empty response body"))
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
