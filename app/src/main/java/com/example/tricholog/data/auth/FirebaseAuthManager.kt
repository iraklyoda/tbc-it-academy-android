package com.example.tricholog.data.auth

import com.example.tricholog.domain.auth.AuthManager
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseAuthManager @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): AuthManager {
    override fun getCurrentUserId(): String? = firebaseAuth.currentUser?.uid
    override fun isUserLoggedIn(): Boolean = firebaseAuth.currentUser != null
}