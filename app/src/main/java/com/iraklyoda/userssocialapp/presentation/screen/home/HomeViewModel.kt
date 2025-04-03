package com.iraklyoda.userssocialapp.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.iraklyoda.userssocialapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _sideEffect: Channel<HomeSideEffect> = Channel()
    val sideEffect: Flow<HomeSideEffect> get () = _sideEffect.receiveAsFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ProfileBtnClicked -> onProfileBtnClicked()
        }
    }

    val usersPagingFlow = userRepository.getUsers().cachedIn(viewModelScope)

    private fun onProfileBtnClicked() {
        viewModelScope.launch {
            _sideEffect.send(HomeSideEffect.NavigateToProfile)
        }
    }
}