package com.example.baseproject.user

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.client.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UsersState(
    var loader: Boolean = false,
    var usersData: List<UserDto> = listOf(),
    var error: String? = null
)

class HomeViewModel: ViewModel() {

    private val _usersStateFlow: MutableStateFlow<UsersState> = MutableStateFlow<UsersState>(
        UsersState()
    )
    val usersStateFlow: StateFlow<UsersState> get() = _usersStateFlow

    fun fetchUsersInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _usersStateFlow.update { it.copy(loader = true) }
            try {
                val response = RetrofitClient.userService.getUsersData()
                if (response.isSuccessful) {
                    val users: List<UserDto> = response.body()!!.data
                    _usersStateFlow.update { it.copy(usersData = users) }
                }
            } catch (e: Exception) {
                d("FetchUsersError", e.message.toString())
                _usersStateFlow.update { it.copy(error = e.message.toString()) }
            } finally {
                _usersStateFlow.update { it.copy(loader = false) }
            }
        }
    }

}