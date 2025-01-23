package com.example.baseproject.data

import android.content.Context

class SessionRepository(context: Context) {
    private val sharedPreferences = context.applicationContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        sharedPreferences.edit().putString("TOKEN_KEY", token).apply()
    }

    fun saveEmail(email: String) {
        sharedPreferences.edit().putString("EMAIL", email).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("TOKEN_KEY", null)
    }

    fun getEmail(): String? {
        return sharedPreferences.getString("EMAIL", null)
    }

    fun clearAttributes() {
        sharedPreferences.edit().remove("TOKEN_KEY").apply()
        sharedPreferences.edit().remove("EMAIL").apply()
    }
}