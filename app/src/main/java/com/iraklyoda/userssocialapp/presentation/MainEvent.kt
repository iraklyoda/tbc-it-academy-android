package com.iraklyoda.userssocialapp.presentation

sealed interface MainEvent {
    data class UpdateCurrentRoute(val route: String?): MainEvent
}