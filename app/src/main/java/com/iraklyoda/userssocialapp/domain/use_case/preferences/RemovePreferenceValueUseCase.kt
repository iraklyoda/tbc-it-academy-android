package com.iraklyoda.userssocialapp.domain.use_case.preferences

import com.iraklyoda.userssocialapp.domain.preferences.PreferenceKey
import com.iraklyoda.userssocialapp.domain.preferences.PreferencesStorage
import javax.inject.Inject

class RemovePreferenceValueUseCase @Inject constructor(
    private val preferencesStorage: PreferencesStorage
) {
    suspend operator fun <T> invoke(key: PreferenceKey<T>) {
        preferencesStorage.removeByKey(key)
    }
}