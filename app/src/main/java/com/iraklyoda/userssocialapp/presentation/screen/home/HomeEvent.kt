package com.iraklyoda.userssocialapp.presentation.screen.home

sealed interface HomeEvent {
    data object ProfileBtnClicked: HomeEvent
}