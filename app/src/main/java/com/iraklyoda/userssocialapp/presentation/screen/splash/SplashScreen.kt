package com.iraklyoda.userssocialapp.presentation.screen.splash

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.iraklyoda.userssocialapp.presentation.utils.CollectSideEffect

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit,
    navigateToHomeScreen:() -> Unit,
    ) {
    CollectSideEffect(flow = viewModel.sideEffect) { effect ->
        when (effect) {
            is SplashSideEffect.NavigateToLogin -> navigateToLoginScreen()
            is SplashSideEffect.NavigateToHome -> navigateToHomeScreen()
        }
    }
}