package com.example.baseproject.presentation.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.baseproject.common.Resource
import com.example.baseproject.data.remote.AuthRepository
import com.example.baseproject.domain.model.ProfileSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginStateFlow: MutableStateFlow<Resource<ProfileSession>> =
        MutableStateFlow<Resource<ProfileSession>>(Resource.Loading(false))

    val loginStateFlow: StateFlow<Resource<ProfileSession>> get() = _loginStateFlow

    fun login(
        email: String,
        password: String,
        rememberMe: Boolean = false,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.login(
                email = email,
                password = password,
                rememberMe = rememberMe
            ).collectLatest { resource ->
                _loginStateFlow.value = resource
            }
        }
    }

    companion object {
        fun Factory(authRepository: AuthRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    LoginViewModel(authRepository)
                }
            }
    }
}