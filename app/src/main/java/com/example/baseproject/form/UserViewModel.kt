package com.example.baseproject.form

import androidx.lifecycle.ViewModel
import com.example.baseproject.User
import com.example.baseproject.datastore.UserDataPreferencesRepository

class UserViewModel(): ViewModel() {
    private val repository = UserDataPreferencesRepository

    suspend fun getUser(): User? {
        return repository.getUser()
    }
}