package com.iraklyoda.userssocialapp.presentation.screen.main.profile

sealed interface ProfileSideEffect {
    data object NavigateToLogin: ProfileSideEffect
}