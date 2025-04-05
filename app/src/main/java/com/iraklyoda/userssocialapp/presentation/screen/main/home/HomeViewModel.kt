package com.iraklyoda.userssocialapp.presentation.screen.main.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.iraklyoda.userssocialapp.domain.use_case.home.GetUsersUseCase
import com.iraklyoda.userssocialapp.presentation.screen.main.home.mapper.toUi
import com.iraklyoda.userssocialapp.presentation.screen.main.home.model.UserUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    val usersPagingFlow: Flow<PagingData<UserUi>> = getUsersUseCase()
        .map { pagingData -> pagingData.map { it.toUi() } }
        .cachedIn(viewModelScope)
}