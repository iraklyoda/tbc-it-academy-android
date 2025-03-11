package com.example.baseproject.domain.preferences

import kotlinx.coroutines.flow.Flow

interface PreferencesStorage {
    suspend fun <T> saveValue(key: PreferenceKey<T>, value: T)
    fun <T> readValue(key: PreferenceKey<T>, defaultValue: T): Flow<T>?
    suspend fun <T> removeByKey(key: PreferenceKey<T>)
}