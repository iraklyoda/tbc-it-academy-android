package com.iraklyoda.userssocialapp.domain.use_case.auth

import com.iraklyoda.userssocialapp.domain.preferences.PreferenceKey
import com.iraklyoda.userssocialapp.domain.use_case.preferences.SavePreferenceValueUseCase

class FakeSavePreferenceValueUseCase(): SavePreferenceValueUseCase {
    override suspend fun <T> invoke(key: PreferenceKey<T>, value: T) {

    }
}