package com.example.baseproject.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

private const val AUTH_PREFERENCES_NAME = "auth_preferences"

private val Context.dataStore by preferencesDataStore(name = AUTH_PREFERENCES_NAME)

class AuthPreferencesRepository(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val EMAIL_KEY = stringPreferencesKey("auth_email")
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun getToken(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }.firstOrNull()
    }

    suspend fun saveEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = email
        }
    }

    suspend fun getEmail(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[EMAIL_KEY]
        }.firstOrNull()
    }


    suspend fun clearAttributes() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }

        context.dataStore.edit { preferences ->
            preferences.remove(EMAIL_KEY)
        }
    }

}