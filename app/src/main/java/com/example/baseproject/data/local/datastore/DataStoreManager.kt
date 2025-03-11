package com.example.baseproject.data.local.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreManager {
    suspend fun <T> saveValue(key: Preferences.Key<T>, value: T)
    fun <T> readValue(key: Preferences.Key<T>, defaultValue: T): Flow<T>?
    suspend fun <T> removeByKey(key: Preferences.Key<T>)
}