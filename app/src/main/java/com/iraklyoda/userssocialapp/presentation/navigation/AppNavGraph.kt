package com.iraklyoda.userssocialapp.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.iraklyoda.userssocialapp.presentation.screen.authentication.login.LoginScreen
import com.iraklyoda.userssocialapp.presentation.screen.authentication.register.RegisterScreen
import com.iraklyoda.userssocialapp.presentation.screen.main.MainScreen
import com.iraklyoda.userssocialapp.presentation.screen.splash.SplashScreen
import kotlinx.serialization.Serializable

@Serializable
data object SplashScreenDestination

@Serializable
data class LoginScreenDestination(
    val email: String? = null,
    val password: String? = null
)

@Serializable
data object RegisterScreenDestination

@Serializable
data object MainScreenDestination

@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = SplashScreenDestination,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {

        // Splash Screen (Entry Point)
        composable<SplashScreenDestination> {
            SplashScreen(
                navigateToLoginScreen = {
                    navController.navigate(LoginScreenDestination()) {
                        popUpTo(SplashScreenDestination) { inclusive = true }
                    }
                },
                navigateToHomeScreen = {
                    navController.navigate(MainScreenDestination) {
                        popUpTo(SplashScreenDestination) { inclusive = true }
                    }
                }
            )
        }

        // Login Screen
        composable<LoginScreenDestination> { backStackEntry ->
            val loginScreenDestination: LoginScreenDestination = backStackEntry.toRoute()

            LoginScreen(
                email = loginScreenDestination.email ?: "",
                password = loginScreenDestination.password ?: "",

                navigateToRegisterScreen = {
                    navController.navigate(RegisterScreenDestination)
                },

                navigateToHomeScreen = {
                    navController.navigate(MainScreenDestination) {
                        popUpTo(LoginScreenDestination()) { inclusive = true }
                    }
                }
            )
        }

        // Register Screen
        composable<RegisterScreenDestination> {
            RegisterScreen(
                navigateToLogin = { email, password ->
                    navController.navigate(
                        LoginScreenDestination(
                            email = email,
                            password = password
                        )
                    ) {
                        popUpTo(SplashScreenDestination) { inclusive = true }
                    }
                }
            )
        }

        // Main Screen
        composable<MainScreenDestination> {
            MainScreen(
                navigateToLogin = {
                    navController.navigate(
                        LoginScreenDestination()
                    ) {
                        popUpTo(MainScreenDestination) { inclusive = true }
                    }
                }
            )
        }
    }
}