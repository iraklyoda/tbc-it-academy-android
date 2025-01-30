package com.example.baseproject.form

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.User
import com.example.baseproject.datastore.UserDataPreferencesRepository
import kotlinx.coroutines.launch

class FormViewModel : ViewModel() {
    private val repository =
        UserDataPreferencesRepository

    fun saveUser(user: User) {
        viewModelScope.launch {
            repository.setUser(user)
        }
    }

    suspend fun getUser(): User? {
        return repository.getUser()
    }
}