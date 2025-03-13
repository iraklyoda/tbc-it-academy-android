package com.example.baseproject.ui.profile

import androidx.lifecycle.ViewModel
import com.example.baseproject.domain.preferences.AppPreferenceKeys
import com.example.baseproject.domain.use_case.preferences.ReadPreferenceValueUseCase
import com.example.baseproject.domain.use_case.preferences.RemovePreferenceValueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val readPreferenceValueUseCase: ReadPreferenceValueUseCase,
    private val removePreferenceValueUseCase: RemovePreferenceValueUseCase
) : ViewModel() {

    suspend fun getEmail(): String? {
        return readPreferenceValueUseCase(key = AppPreferenceKeys.EMAIL_KEY, defaultValue = "")?.firstOrNull()
    }

    suspend fun clearSession() {
        removePreferenceValueUseCase(key = AppPreferenceKeys.TOKEN_KEY)
        removePreferenceValueUseCase(key = AppPreferenceKeys.EMAIL_KEY)
    }
}

