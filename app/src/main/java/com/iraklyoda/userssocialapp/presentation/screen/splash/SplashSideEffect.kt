package com.iraklyoda.userssocialapp.presentation.screen.splash

sealed interface SplashSideEffect {
    data object NavigateToLogin: SplashSideEffect
    data object NavigateToHome: SplashSideEffect
}