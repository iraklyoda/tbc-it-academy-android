package com.example.baseproject.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.baseproject.client.RetrofitClient

private const val USER_PER_PAGE = 6

class HomeViewModel : ViewModel() {

    val usersPagerFlow = Pager(
        config = PagingConfig(
            pageSize = USER_PER_PAGE
        )
    ) {
        UserPagingSource(RetrofitClient.userService)
    }.flow.cachedIn(viewModelScope)
}