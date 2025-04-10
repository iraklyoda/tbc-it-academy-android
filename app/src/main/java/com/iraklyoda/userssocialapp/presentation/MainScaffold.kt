package com.iraklyoda.userssocialapp.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.iraklyoda.userssocialapp.presentation.navigation.AppNavGraph
import com.iraklyoda.userssocialapp.presentation.navigation.BottomNavBar

@Composable
fun MainScaffold(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        mainViewModel.onEvent(MainEvent.UpdateCurrentRoute(route = currentRoute))
    }

    Scaffold(
        bottomBar = {
            if (mainViewModel.uiState.showBottomBar) {
                BottomNavBar(navController)
            }
        }
    ) { innerPadding ->
        AppNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}