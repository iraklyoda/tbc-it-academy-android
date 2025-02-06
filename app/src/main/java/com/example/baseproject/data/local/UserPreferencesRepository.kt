package com.example.baseproject.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private const val USER_PREFERENCES_NAME = "user_preferences"

private val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

class UserPreferencesRepository(context: Context) {

    private object PreferencesKeys {
        val SUSPENDED_UNTIL = longPreferencesKey("suspended_until")
        val BEEN_SUSPENDED = booleanPreferencesKey("been_suspended")
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferencesRepository? = null

        fun getInstance(context: Context): UserPreferencesRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserPreferencesRepository(context).also {
                    INSTANCE = it
                }
            }
        }
    }

    private val dataStore = context.dataStore

    suspend fun setSuspensionTime(suspendedUntil: Long) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SUSPENDED_UNTIL] = suspendedUntil
            preferences[PreferencesKeys.BEEN_SUSPENDED] = true
        }
    }

    suspend fun isUserSuspended(): Boolean {
        val preferences = dataStore.data.first()
        val suspendedUntil = preferences[PreferencesKeys.SUSPENDED_UNTIL] ?: 0L
        return System.currentTimeMillis() < suspendedUntil
    }

    suspend fun hasUserBeenSuspended(): Boolean {
        return dataStore.data.first()[PreferencesKeys.BEEN_SUSPENDED] ?: false
    }

    suspend fun clearBeenSuspended() {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.BEEN_SUSPENDED] = false
        }
    }

    suspend fun clearSuspension() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.SUSPENDED_UNTIL)
            preferences.remove(PreferencesKeys.BEEN_SUSPENDED)
        }
    }
}