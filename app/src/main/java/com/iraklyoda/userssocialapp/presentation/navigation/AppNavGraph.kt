package com.iraklyoda.userssocialapp.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.iraklyoda.userssocialapp.presentation.screen.authentication.login.LoginScreenDestination
import com.iraklyoda.userssocialapp.presentation.screen.authentication.login.loginNavGraph
import com.iraklyoda.userssocialapp.presentation.screen.authentication.register.RegisterScreenDestination
import com.iraklyoda.userssocialapp.presentation.screen.authentication.register.registerNavGraph
import com.iraklyoda.userssocialapp.presentation.screen.main.home.HomeScreenDestination
import com.iraklyoda.userssocialapp.presentation.screen.main.home.homeNavGraph
import com.iraklyoda.userssocialapp.presentation.screen.main.profile.profileNavGraph
import com.iraklyoda.userssocialapp.presentation.screen.splash.SplashScreenDestination
import com.iraklyoda.userssocialapp.presentation.screen.splash.splashNavGraph

@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = SplashScreenDestination,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {

        // Splash
        splashNavGraph(
            navigateToHomeScreen = {
                navController.navigate(HomeScreenDestination) {
                    popUpTo(SplashScreenDestination) { inclusive = true }
                }
            },

            navigateToLoginScreen = {
                navController.navigate(LoginScreenDestination()) {
                    popUpTo(SplashScreenDestination) { inclusive = true }
                }
            }
        )

        // Login
        loginNavGraph(
            navigateToRegisterScreen = { navController.navigate(RegisterScreenDestination) },
            navigateToHomeScreen = { navController.navigate(HomeScreenDestination) }
        )

        // Register
        registerNavGraph(
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

        // Bottom Nav Bar
        // home
        homeNavGraph()

        // profile
        profileNavGraph(
            navigateToLogin = {
                navController.navigate(
                    LoginScreenDestination()
                )
            }
        )
    }
}