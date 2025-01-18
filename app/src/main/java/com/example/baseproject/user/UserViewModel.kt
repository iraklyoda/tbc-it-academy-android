package com.example.baseproject.user

import android.util.Log.d
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.client.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class UserViewModel : ViewModel() {
    private val _user = MutableLiveData<UserDto>()
    val user: LiveData<UserDto> get() = _user

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun registerUser(user: UserDto) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.userService.registerUser(user = user)

                if (response.isSuccessful) {
                    d("userRegister", "user created")
                    _user.postValue(
                        UserDto(
                            id = response.body()?.id,
                            email = user.email,
                            password = user.password
                        )
                    )
                } else {
                    _error.postValue( response.errorBody()?.string() ?: "Errored")
                    d("userRegister", response.errorBody()?.string() ?: "who knows")
                }
            } catch (e: Exception) {
                d("userRegister", e.message.toString())
                d("userRegister", e.cause.toString())
            }
        }
    }

}