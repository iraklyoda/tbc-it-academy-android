package com.example.baseproject.domain.preference_key

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    private val TOKEN_KEY = stringPreferencesKey("auth_token")
    private val EMAIL_KEY = stringPreferencesKey("auth_email")
}