package com.iraklyoda.userssocialapp.presentation.screen.authentication.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class LoginScreenDestination(
    val email: String? = null,
    val password: String? = null
)

fun NavGraphBuilder.loginNavGraph(
    navigateToRegisterScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    composable<LoginScreenDestination> { backStackEntry ->
        val loginScreenDestination: LoginScreenDestination = backStackEntry.toRoute()

        LoginScreen(
            email = loginScreenDestination.email ?: "",
            password = loginScreenDestination.password ?: "",
            navigateToRegisterScreen = navigateToRegisterScreen,
            navigateToHomeScreen = navigateToHomeScreen
        )
    }
}