package com.example.baseproject.ui.profile

import androidx.lifecycle.ViewModel
import com.example.baseproject.data.local.AuthPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authPreferencesRepository: AuthPreferencesRepository
): ViewModel() {

    suspend fun getEmail(): String? {
        return authPreferencesRepository.getEmail()
    }

    suspend fun clearPreferencesAttributes() {
        authPreferencesRepository.clearAttributes()
    }
}