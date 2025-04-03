package com.iraklyoda.userssocialapp.presentation.screen.profile

sealed interface ProfileEvent {
    data object LogOutBtnClicked: ProfileEvent
}