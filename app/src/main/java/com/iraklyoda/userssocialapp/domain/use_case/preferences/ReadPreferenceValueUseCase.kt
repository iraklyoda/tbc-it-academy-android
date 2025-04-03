package com.iraklyoda.userssocialapp.domain.use_case.preferences

import com.iraklyoda.userssocialapp.domain.preferences.PreferenceKey
import com.iraklyoda.userssocialapp.domain.preferences.PreferencesStorage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadPreferenceValueUseCase @Inject constructor(
    private val preferencesStorage: PreferencesStorage
) {
    suspend operator fun <T> invoke(key: PreferenceKey<T>): Flow<T?> {
        return preferencesStorage.readValue(key = key)
    }
}