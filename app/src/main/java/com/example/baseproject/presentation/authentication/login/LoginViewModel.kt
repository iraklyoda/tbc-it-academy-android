package com.example.baseproject.presentation.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.baseproject.common.ApiHelper
import com.example.baseproject.common.Resource
import com.example.baseproject.data.local.AuthPreferencesRepository
import com.example.baseproject.data.remote.api.RetrofitClient
import com.example.baseproject.data.remote.dto.ProfileDto
import com.example.baseproject.presentation.authentication.AuthState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authPreferencesRepository: AuthPreferencesRepository
) : ViewModel() {

    private val _loginStateFlow: MutableStateFlow<AuthState> =
        MutableStateFlow<AuthState>(AuthState())
    val loginStateFlow: StateFlow<AuthState> get() = _loginStateFlow

    fun loginUser(
        profileDto: ProfileDto,
        rememberMe: Boolean = false,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _loginStateFlow.update { it.copy(loader = true) }
            val result =
                ApiHelper.handleHttpRequest { RetrofitClient.userService.loginUser(profileDto) }
            when (result) {
                is Resource.Success -> {
                    handleLoginSuccess(
                        rememberMe = rememberMe,
                        token = result.data.token,
                        profileData = profileDto
                    )
                }
                is Resource.Error -> {
                    _loginStateFlow.update { it.copy(error = result.errorMessage) }
                }
            }
            _loginStateFlow.update { it.copy(loader = false) }
        }
    }

    private suspend fun handleLoginSuccess  (
        rememberMe: Boolean,
        token: String,
        profileData: ProfileDto
    ) {
        if (rememberMe && token.isNotEmpty()) {
            authPreferencesRepository.saveToken(token)
        }
        authPreferencesRepository.saveEmail(profileData.email)
        _loginStateFlow.update { it.copy(userInfo = profileData) }
    }

    companion object {
        fun Factory(authPreferencesRepository: AuthPreferencesRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    LoginViewModel(authPreferencesRepository)
                }
            }
    }
}