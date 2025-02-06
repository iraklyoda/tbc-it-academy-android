package com.example.baseproject.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.baseproject.data.local.UserPreferencesRepository

class SecurityViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val suspensionTimeMillis: Long = 30 * 1000
    private val passcode = "0934"

    private var suspendedUntil: Long = 0

    suspend fun suspendUser(again: Boolean = false) {
        val currentTime = System.currentTimeMillis()
        suspendedUntil = if (again)
            currentTime + suspensionTimeMillis * 2
        else
            currentTime + suspensionTimeMillis
        userPreferencesRepository.setSuspensionTime(suspendedUntil)
    }

    suspend fun checkSuspend(): Boolean {
        return userPreferencesRepository.isUserSuspended()
    }
    
    suspend fun checkUserBeenSuspended(): Boolean {
        return userPreferencesRepository.hasUserBeenSuspended()
    }
    
    suspend fun clearUserBeenSuspended() {
        return userPreferencesRepository.clearBeenSuspended()
    }

    suspend fun clearSuspension() {
        userPreferencesRepository.clearSuspension()
    }

    fun checkPasscode(passcode: String): Boolean {
        return passcode == this.passcode
    }

    companion object {
        fun Factory(userPreferencesRepository: UserPreferencesRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    SecurityViewModel(userPreferencesRepository)
                }
            }
    }
}