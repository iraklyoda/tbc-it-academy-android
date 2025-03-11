package com.example.baseproject.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.baseproject.domain.preferences.PreferenceKey
import com.example.baseproject.domain.preferences.PreferencesStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): PreferencesStorage {
    override suspend fun <T> saveValue(key: PreferenceKey<T>, value: T) {
        dataStore.edit { preference ->
            val dsKey = createDataStoreKey(key)
            preference[dsKey] = value
        }
    }

    override fun <T> readValue(key: PreferenceKey<T>, defaultValue: T): Flow<T> {
        return dataStore.data.map {
            val dsKey = createDataStoreKey(key)
            it[dsKey] ?: defaultValue
        }
    }

    override suspend fun <T> removeByKey(key: PreferenceKey<T>) {
        dataStore.edit { preferences ->
            val dsKey = createDataStoreKey(key)
            preferences.remove(dsKey)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> createDataStoreKey(key: PreferenceKey<T>): Preferences.Key<T> {
        return when (key) {
            is PreferenceKey.StringKey -> stringPreferencesKey(key.keyName)
            is PreferenceKey.IntKey -> intPreferencesKey(key.keyName)
            is PreferenceKey.BooleanKey -> booleanPreferencesKey(key.keyName)
        } as Preferences.Key<T>
    }
}