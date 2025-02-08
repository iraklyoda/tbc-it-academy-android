package com.example.baseproject.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.baseproject.data.remote.api.RetrofitClient
import com.example.baseproject.data.paging.UserPagingSource

private const val USER_PER_PAGE = 6

class HomeViewModel : ViewModel() {

    val usersPagerFlow = Pager(
        config = PagingConfig(
            pageSize = USER_PER_PAGE,
            prefetchDistance = 1
        )
    ) {
        UserPagingSource(RetrofitClient.userService)
    }.flow.cachedIn(viewModelScope)
}