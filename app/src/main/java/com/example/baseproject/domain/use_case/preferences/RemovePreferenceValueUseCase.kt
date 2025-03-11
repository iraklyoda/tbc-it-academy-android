package com.example.baseproject.domain.use_case.preferences

import com.example.baseproject.domain.preferences.PreferenceKey
import com.example.baseproject.domain.preferences.PreferencesStorage
import javax.inject.Inject

class RemovePreferenceValueUseCase @Inject constructor(
    private val preferencesStorage: PreferencesStorage
) {
    suspend operator fun <T> invoke(key: PreferenceKey<T>) {
        preferencesStorage.removeByKey(key)
    }
}