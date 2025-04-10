package com.iraklyoda.userssocialapp.presentation.screen.authentication.register

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object RegisterScreenDestination

fun NavGraphBuilder.registerNavGraph(
    navigateToLogin: (String, String) -> Unit
) {
    composable<RegisterScreenDestination> {
        RegisterScreen(
            navigateToLogin = navigateToLogin
        )
    }
}