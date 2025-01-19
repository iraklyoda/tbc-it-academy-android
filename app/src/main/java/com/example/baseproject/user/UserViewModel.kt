package com.example.baseproject.user

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.client.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _users = MutableLiveData<MutableList<UserDto>>(
        mutableListOf<UserDto>()
    )
    private val users: LiveData<MutableList<UserDto>> get() = _users

    private val _user = MutableLiveData<UserDto?>()
    val user: LiveData<UserDto?> get() = _user

    private val _error = MutableLiveData<UserErrors?>()
    val error: LiveData<UserErrors?> get() = _error

    fun logout() {
        _user.value = null
    }

    fun registerUser(user: UserDto) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.userService.registerUser(user = user)

                if (response.isSuccessful) {
                    if (isEmailUnique(user.email)) {
                        val registeredUser: UserDto = UserDto(
                            id = response.body()?.id,
                            username = user.username,
                            email = user.email,
                            password = user.password
                        )

                        _users.value?.add(registeredUser)
                        _user.postValue(registeredUser)
                    } else {
                        setError(UserErrors.USER_ALREADY_EXISTS)
                    }
                } else {
                    d("userRegister", response.errorBody()?.string() ?: "error")
                }
            } catch (e: Exception) {
                d("userRegister", e.message.toString())
            }
        }
    }

    fun loginUser(username: String, password: String) {
        val loggedInUser: UserDto? =
            users.value?.find { it.username == username && it.password == password }

        if (loggedInUser != null) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = RetrofitClient.userService.loginUser(user = loggedInUser)
                    if (response.isSuccessful) {
                        _user.postValue(loggedInUser)
                    }
                } catch (e: Exception) {
                    d("userRegister", e.message.toString())
                }
            }
        } else {
            setError(UserErrors.WRONG_CREDENTIALS)
        }
    }

    private fun setError(error: UserErrors) {
        _error.postValue(error)
    }

    fun clearError() {
        _error.value = null
    }

    private fun isEmailUnique(email: String): Boolean {
        return users.value?.none { it.email == email } ?: true
    }

}