package com.example.baseproject.domain.use_case.preferences

import com.example.baseproject.domain.preferences.PreferenceKey
import com.example.baseproject.domain.preferences.PreferencesStorage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadPreferenceValueUseCase @Inject constructor(
    private val preferencesStorage: PreferencesStorage
) {
    suspend operator fun <T> invoke(key: PreferenceKey<T>, defaultValue: T): Flow<T>? {
        return preferencesStorage.readValue(key = key, defaultValue = defaultValue)
    }
}