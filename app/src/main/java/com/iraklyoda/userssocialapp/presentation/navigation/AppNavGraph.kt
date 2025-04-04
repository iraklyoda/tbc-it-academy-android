package com.iraklyoda.userssocialapp.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.iraklyoda.userssocialapp.presentation.screen.authentication.login.LoginScreen
import com.iraklyoda.userssocialapp.presentation.screen.authentication.register.RegisterScreen
import com.iraklyoda.userssocialapp.presentation.screen.home.HomeScreen
import com.iraklyoda.userssocialapp.presentation.screen.profile.ProfileScreen
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
data object HomeScreenDestination

@Serializable
data object ProfileScreenDestination

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController, startDestination = SplashScreenDestination,
        enterTransition = { EnterTransition.None }, exitTransition = { ExitTransition.None }
    ) {

        // Splash Screen (Entry Point)
        composable<SplashScreenDestination> {
            SplashScreen(
                navigateToLoginScreen = {
                    navController.navigate(LoginScreenDestination())
                },
                navigateToHomeScreen = {
                    navController.navigate(HomeScreenDestination)
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
                    navController.navigate(HomeScreenDestination) {
                        popUpTo(SplashScreenDestination) { inclusive = true }
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
                    )
                }
            )
        }

        // Home Screen
        composable<HomeScreenDestination> {
            HomeScreen(
                navigateToProfile = {
                    navController.navigate(
                        ProfileScreenDestination
                    )
                }
            )
        }

        // Profile Screen
        composable<ProfileScreenDestination> {
            ProfileScreen(navigateToLogin = {
                navController.navigate(LoginScreenDestination()) {
                    popUpTo(SplashScreenDestination) { inclusive = true }
                }
            })
        }
    }
}