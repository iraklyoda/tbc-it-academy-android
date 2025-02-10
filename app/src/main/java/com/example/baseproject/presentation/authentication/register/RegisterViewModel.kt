package com.example.baseproject.presentation.authentication.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.baseproject.common.Resource
import com.example.baseproject.data.remote.AuthRepository
import com.example.baseproject.domain.model.Profile
import com.example.baseproject.presentation.authentication.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _registerStateFlow: MutableStateFlow<Resource<Profile>> =
        MutableStateFlow<Resource<Profile>>(Resource.Loading(false))

    val registerStateFlow: StateFlow<Resource<Profile>> get() = _registerStateFlow

    fun register(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.register(
                email = email,
                password = password
            ).collectLatest { resource ->
                _registerStateFlow.value = resource
            }
        }
    }

    companion object {
        fun Factory(authRepository: AuthRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    RegisterViewModel(authRepository)
                }
            }
    }

}