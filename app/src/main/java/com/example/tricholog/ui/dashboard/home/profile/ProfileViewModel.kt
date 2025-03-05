package com.example.tricholog.ui.dashboard.home.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tricholog.data.repositories.auth.AuthRepositoryImpl
import com.example.tricholog.domain.model.User
import com.example.tricholog.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepositoryImpl: AuthRepositoryImpl
) : ViewModel() {
    val userState: MutableStateFlow<User?> = MutableStateFlow(User())

    val logoutState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    fun logout() {
        viewModelScope.launch {
            try {
                authRepositoryImpl.logout()
                logoutState.value = true
            } catch (e: Exception) {
                Log.e("Auth", "Logout failed: ${e.message}")
                logoutState.value = false
            }
        }
    }

    init {
        viewModelScope.launch {
            userState.value = userRepository.getUser()
        }
    }
}