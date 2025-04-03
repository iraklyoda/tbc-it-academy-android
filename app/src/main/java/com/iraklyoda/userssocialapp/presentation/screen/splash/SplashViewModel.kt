package com.iraklyoda.userssocialapp.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iraklyoda.userssocialapp.domain.preferences.AppPreferenceKeys
import com.iraklyoda.userssocialapp.domain.use_case.preferences.ReadPreferenceValueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val readPreferenceValueUseCase: ReadPreferenceValueUseCase
): ViewModel() {

    private val _sideEffect: Channel<SplashSideEffect> = Channel()
    val sideEffect: Flow<SplashSideEffect> get() = _sideEffect.receiveAsFlow()

    init {
        readSession()
    }

    // Check token
    private fun readSession() {
        viewModelScope.launch {
            readPreferenceValueUseCase(key = AppPreferenceKeys.TOKEN_KEY).collect {
                if(it.isNullOrEmpty())
                    _sideEffect.send(SplashSideEffect.NavigateToLogin)
                else
                    _sideEffect.send(SplashSideEffect.NavigateToHome)
            }
        }
    }
}