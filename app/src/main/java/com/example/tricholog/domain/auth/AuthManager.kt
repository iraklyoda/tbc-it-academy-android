package com.example.tricholog.domain.auth

interface AuthManager {
    fun getCurrentUserId(): String?
    fun isUserLoggedIn(): Boolean
}