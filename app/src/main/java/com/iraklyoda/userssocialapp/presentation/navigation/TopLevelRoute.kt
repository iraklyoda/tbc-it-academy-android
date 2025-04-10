package com.iraklyoda.userssocialapp.presentation.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class TopLevelRoute<T : Any>(
    val nameRes: Int,
    val route: T,
    val icon: ImageVector
)
