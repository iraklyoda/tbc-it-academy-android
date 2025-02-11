package com.example.baseproject.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.cachedIn
import com.example.baseproject.data.UserRepository

class HomeViewModel(
    userRepository: UserRepository
) : ViewModel() {

    val usersPagingFlow = userRepository.getUsers().cachedIn(viewModelScope)

    companion object {
        fun Factory(userRepository: UserRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    HomeViewModel(userRepository)
                }
            }
    }
}