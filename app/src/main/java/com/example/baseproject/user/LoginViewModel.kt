package com.example.baseproject.user

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.baseproject.client.RetrofitClient
import com.example.baseproject.data.AuthPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authPreferencesRepository: AuthPreferencesRepository
) : ViewModel() {

    private val _authStateFlow: MutableStateFlow<AuthState> = MutableStateFlow<AuthState>(AuthState())
    val authStateFlow: StateFlow<AuthState> get() = _authStateFlow

    fun loginUser(
        profileDto: ProfileDto, rememberMe: Boolean = false,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _authStateFlow.update { it.copy(loader = true) }
            try {
                val response = RetrofitClient.userService.loginUser(profileDto)

                if (response.isSuccessful) {
                    val token = response.body()?.token
                    if (rememberMe && !token.isNullOrEmpty()) {
                        authPreferencesRepository.saveToken(token)
                    }
                    authPreferencesRepository.saveEmail(profileDto.email)
                    _authStateFlow.update { it.copy(userInfo = profileDto) }
                    d("userRegister", "Success")
                } else {
                    _authStateFlow.update { it.copy(error = response.errorBody().toString()) }
                    d("userRegister", "no success")
                }

            } catch (e: Exception) {
                _authStateFlow.update { it.copy(error = e.message.toString()) }
                d("userRegister", e.message.toString())
            } finally {
                _authStateFlow.update { it.copy(loader = false) }
            }
        }
    }
}

class LoginViewModelFactory(
    private val authPreferencesRepository: AuthPreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(authPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}