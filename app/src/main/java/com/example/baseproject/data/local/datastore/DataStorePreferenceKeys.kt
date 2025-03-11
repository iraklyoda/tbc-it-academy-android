package com.example.baseproject.data.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStorePreferenceKeys {
    val TOKEN_KEY = stringPreferencesKey("auth_token")
    val EMAIL_KEY = stringPreferencesKey("auth_email")
}