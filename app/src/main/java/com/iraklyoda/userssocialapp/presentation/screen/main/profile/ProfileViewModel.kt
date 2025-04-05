package com.iraklyoda.userssocialapp.presentation.screen.main.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iraklyoda.userssocialapp.domain.preferences.AppPreferenceKeys
import com.iraklyoda.userssocialapp.domain.use_case.preferences.ReadPreferenceValueUseCase
import com.iraklyoda.userssocialapp.domain.use_case.preferences.RemovePreferenceValueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val readPreferenceValueUseCase: ReadPreferenceValueUseCase,
    private val removePreferenceValueUseCase: RemovePreferenceValueUseCase
) : ViewModel() {

    private val _sideEffect: Channel<ProfileSideEffect> = Channel()
    val sideEffect: Flow<ProfileSideEffect> get() = _sideEffect.receiveAsFlow()

    var state by mutableStateOf(ProfileState())
        private set

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LogOutBtnClicked -> onLogOutBtnClick()
        }
    }

    private fun onLogOutBtnClick() {
        viewModelScope.launch {
            clearSession()
            _sideEffect.send(ProfileSideEffect.NavigateToLogin)
        }
    }

    init {
        viewModelScope.launch { state = state.copy(userEmail = getEmail()) }
    }

    suspend fun getEmail(): String? {
        return readPreferenceValueUseCase(key = AppPreferenceKeys.EMAIL_KEY).firstOrNull()
    }

    private suspend fun clearSession() {
        removePreferenceValueUseCase(key = AppPreferenceKeys.TOKEN_KEY)
        removePreferenceValueUseCase(key = AppPreferenceKeys.EMAIL_KEY)
    }
}

