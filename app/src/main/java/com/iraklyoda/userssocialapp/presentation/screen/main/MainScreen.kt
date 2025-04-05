package com.iraklyoda.userssocialapp.presentation.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.iraklyoda.userssocialapp.R
import com.iraklyoda.userssocialapp.presentation.navigation.HomeScreenDestination
import com.iraklyoda.userssocialapp.presentation.navigation.MainNavGraph
import com.iraklyoda.userssocialapp.presentation.navigation.ProfileScreenDestination
import com.iraklyoda.userssocialapp.presentation.navigation.TopLevelRoute

@Composable
fun MainScreen(
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val nestedNavController = rememberNavController()

    val topLevelRoutes = listOf(
        TopLevelRoute(stringResource(R.string.home), HomeScreenDestination, Icons.Default.Home),
        TopLevelRoute(
            stringResource(R.string.profile),
            ProfileScreenDestination,
            Icons.Default.Person
        )
    )

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by nestedNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                topLevelRoutes.forEach() { topLevelRoute ->
                    val isSelected = topLevelRoute.route::class.simpleName?.let {
                        currentDestination?.route?.endsWith(
                            it
                        )
                    } == true

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            nestedNavController.navigate(topLevelRoute.route) {
                                popUpTo(nestedNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                topLevelRoute.icon,
                                contentDescription = topLevelRoute.name
                            )
                        },
                        label = { Text(topLevelRoute.name) },
                    )
                }
            }
        }
    ) { innerPadding ->
        MainNavGraph(
            nestedNavController = nestedNavController,
            navigateToLogin = {
                navigateToLogin()
            },
            modifier = Modifier.padding(innerPadding)
        )

    }
}