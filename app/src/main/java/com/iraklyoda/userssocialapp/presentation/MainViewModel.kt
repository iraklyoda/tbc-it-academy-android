package com.iraklyoda.userssocialapp.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.iraklyoda.userssocialapp.R
import com.iraklyoda.userssocialapp.presentation.navigation.TopLevelRoute
import com.iraklyoda.userssocialapp.presentation.screen.main.home.HomeScreenDestination
import com.iraklyoda.userssocialapp.presentation.screen.main.profile.ProfileScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    var uiState by mutableStateOf(MainUiState())
        private set

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.UpdateCurrentRoute -> updateCurrentRoute(route = event.route)
        }
    }

    private fun updateCurrentRoute(route: String?) {
        val shouldShowBottomBar = topLevelRoutes.any { topLevelRoute ->
            route?.endsWith(topLevelRoute.route::class.simpleName ?: "") == true
        }

        uiState = uiState.copy(
            currentRoute = route,
            showBottomBar = shouldShowBottomBar
        )
    }

    companion object {
        val topLevelRoutes = listOf(
            TopLevelRoute(R.string.home, HomeScreenDestination, Icons.Default.Home),
            TopLevelRoute(
                R.string.profile,
                ProfileScreenDestination,
                Icons.Default.Person
            )
        )
    }
}