package com.example.baseproject.presentation.authentication.register

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.common.ApiHelper
import com.example.baseproject.common.Resource
import com.example.baseproject.data.remote.api.RetrofitClient
import com.example.baseproject.data.remote.dto.ProfileDto
import com.example.baseproject.presentation.authentication.AuthState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _registerStateFlow: MutableStateFlow<AuthState> =
        MutableStateFlow<AuthState>(AuthState())
    val registerStateFlow: StateFlow<AuthState> get() = _registerStateFlow

    fun registerUser(profileDto: ProfileDto) {

        viewModelScope.launch(Dispatchers.IO) {
            _registerStateFlow.update { it.copy(loader = true) }
            val result = ApiHelper.handleHttpRequest { RetrofitClient.userService.registerUser(profileDto) }

            when (result) {
                is Resource.Success -> {
                    _registerStateFlow.update { it.copy(userInfo = profileDto) }
                }
                is Resource.Error -> {
                    _registerStateFlow.update { it.copy(error = result.errorMessage) }
                }
            }
            _registerStateFlow.update { it.copy(loader = false) }
        }
    }

}