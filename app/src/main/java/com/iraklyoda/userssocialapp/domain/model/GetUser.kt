package com.iraklyoda.userssocialapp.domain.model

data class GetUser(
    val id: Int,
    val fullName: String,
    val email: String,
    val avatarUrl: String
)