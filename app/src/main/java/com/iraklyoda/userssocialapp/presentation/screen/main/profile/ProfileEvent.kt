package com.iraklyoda.userssocialapp.presentation.screen.main.profile

sealed interface ProfileEvent {
    data object LogOutBtnClicked: ProfileEvent
}