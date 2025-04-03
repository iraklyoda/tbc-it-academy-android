package com.iraklyoda.userssocialapp.presentation.screen.profile

sealed interface ProfileSideEffect {
    data object NavigateToLogin: ProfileSideEffect
}