package com.example.baseproject.domain.use_case.preferences

import com.example.baseproject.domain.preferences.PreferenceKey
import com.example.baseproject.domain.preferences.PreferencesStorage
import javax.inject.Inject

class SavePreferenceValueUseCase @Inject constructor(
    private val preferencesStorage: PreferencesStorage
) {
    suspend operator fun <T> invoke(key: PreferenceKey<T>, value: T) {
        preferencesStorage.saveValue(key, value)
    }
}