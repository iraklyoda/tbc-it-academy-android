package com.example.baseproject.user

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.client.RetrofitClient
import com.example.baseproject.data.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    fun loginUser(userDto: UserDto, rememberMe: Boolean = false,
                  onSuccess: suspend () -> Unit,
                  onError: suspend (error: String) -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val response = RetrofitClient.userService.loginUser(userDto)

                if (response.isSuccessful) {
                    val token = response.body()?.token
                    if (rememberMe && !token.isNullOrEmpty()) {
                        sessionRepository.saveToken(token)
                    }
                    sessionRepository.saveEmail(userDto.email)
                    onSuccess()
                    d("userRegister", "Success")
                } else {
                    onError(response.errorBody().toString())
                    d("userRegister", "no success")
                }

            } catch (e: Exception) {
                onError(e.message.toString())
                d("userRegister", e.message.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }
}