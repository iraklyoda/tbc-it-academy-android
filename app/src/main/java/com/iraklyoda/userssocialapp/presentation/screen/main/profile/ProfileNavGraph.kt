package com.iraklyoda.userssocialapp.presentation.screen.main.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ProfileScreenDestination

fun NavGraphBuilder.profileNavGraph(
    navigateToLogin: () -> Unit
) {
    composable<ProfileScreenDestination> {
        ProfileScreen(navigateToLogin = navigateToLogin)
    }
}