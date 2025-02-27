package com.example.tricholog.ui.dashboard.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tricholog.domain.model.User
import com.example.tricholog.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> get() = _currentUser

    private suspend fun getUser() {
        _currentUser.value = userRepository.getUser()
        Log.d("HomeFragmentUser", "${currentUser}")

    }

    init {
        viewModelScope.launch {
            getUser()
        }
    }
}