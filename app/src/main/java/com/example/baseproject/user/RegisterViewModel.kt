package com.example.baseproject.user

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.client.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    private val _registrationSuccess: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val registrationSuccess: MutableStateFlow<Boolean> = _registrationSuccess

    fun registerUser(profileDto: ProfileDto, onFailed: suspend (error: String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            try {
                val response = RetrofitClient.userService.registerUser(profileDto)

                if (response.isSuccessful) {
                    d("userRegister", "Registration success")
                    _registrationSuccess.value = true
                } else {
                    _registrationSuccess.value = false
                    onFailed(response.errorBody().toString())
                    d("userRegister", "Error ${response.errorBody()}")
                }

            } catch (e: Exception) {
                d("userRegister", e.message.toString())
                onFailed(e.message.toString())
                _registrationSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

}