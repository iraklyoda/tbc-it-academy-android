package com.iraklyoda.userssocialapp.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.iraklyoda.userssocialapp.presentation.MainViewModel.Companion.topLevelRoutes

@Composable
fun BottomNavBar(navController: NavController) {

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
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
                    navController.navigate(topLevelRoute.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        topLevelRoute.icon,
                        contentDescription = stringResource(topLevelRoute.nameRes)
                    )
                },
                label = { Text(stringResource(topLevelRoute.nameRes)) },
            )
        }
    }
}