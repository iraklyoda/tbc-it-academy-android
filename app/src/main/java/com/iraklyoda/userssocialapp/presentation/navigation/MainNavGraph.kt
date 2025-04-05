package com.iraklyoda.userssocialapp.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.iraklyoda.userssocialapp.presentation.screen.main.home.HomeScreen
import com.iraklyoda.userssocialapp.presentation.screen.main.profile.ProfileScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreenDestination

@Serializable
data object ProfileScreenDestination

@Composable
fun MainNavGraph(
    nestedNavController: NavHostController,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = nestedNavController,
        startDestination = HomeScreenDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        modifier = modifier
    ) {
        // Home Screen
        composable<HomeScreenDestination> {
            HomeScreen()
        }

        // Profile Screen
        composable<ProfileScreenDestination> {
            ProfileScreen(navigateToLogin = {
                navigateToLogin()
            })
        }
    }
}