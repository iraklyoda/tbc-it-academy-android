package com.iraklyoda.userssocialapp.presentation.navigation.common

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun <T> NavHost(
    navController: NavController,
    startDestination: T,
    modifier: Modifier,
    enterTransition: () -> EnterTransition = { EnterTransition.None },
    exitTransition: () -> ExitTransition = { ExitTransition.None }
) {
}