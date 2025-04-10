package com.iraklyoda.userssocialapp.presentation.screen.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SplashScreenDestination


fun NavGraphBuilder.splashNavGraph(
    navigateToLoginScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    composable<SplashScreenDestination> {
        SplashScreen(
            navigateToLoginScreen = navigateToLoginScreen,
            navigateToHomeScreen = navigateToHomeScreen
        )
    }
}