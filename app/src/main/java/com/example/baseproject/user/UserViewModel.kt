package com.example.baseproject.user

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.baseproject.data.DataRepository
import com.example.baseproject.data.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UIState {
    data class Loading(val loading: Boolean): UIState()
    data class Success(val users: Flow<List<UserEntity>>): UIState()
    data class Error(val message: String) : UIState()
    data class Default(val boolean: Boolean): UIState()
}

class UserViewModel(private val dataRepository: DataRepository) : ViewModel() {
    val users: Flow<List<UserEntity>> = dataRepository.getUsers()

    private val _usersState = MutableStateFlow<UIState>(UIState.Default(true))
    val usersState: StateFlow<UIState> = _usersState

    fun refreshUsers() {
        viewModelScope.launch {
            try {
                _usersState.value = UIState.Loading(true)
                dataRepository.fetchUsers()
                _usersState.value = UIState.Success(users)
            } catch (e: Exception) {
                d("errorUserFetching", e.toString())
                _usersState.value = UIState.Error(e.message ?: "Who knows")
            }
        }
    }


    companion object {
        fun Factory(dataRepository: DataRepository): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                UserViewModel(dataRepository)
            }
        }
    }
}