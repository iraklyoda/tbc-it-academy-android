package com.iraklyoda.userssocialapp.presentation.screen.home

import androidx.paging.PagingData
import com.iraklyoda.userssocialapp.presentation.screen.home.model.UserUi

data class HomeState(
    val loader: Boolean = false,
    val users: PagingData<UserUi>? = null,
    val apiError: String? = null
)