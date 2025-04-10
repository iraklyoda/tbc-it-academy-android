package com.iraklyoda.userssocialapp.presentation.screen.main.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreenDestination

fun NavGraphBuilder.homeNavGraph() {
    composable<HomeScreenDestination> {
        HomeScreen()
    }
}