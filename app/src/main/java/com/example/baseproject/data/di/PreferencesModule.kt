package com.example.baseproject.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.baseproject.data.local.datastore.DataStoreManagerImpl
import com.example.baseproject.domain.preferences.PreferencesStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

private const val AUTH_PREFERENCES_NAME = "auth_preferences"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AUTH_PREFERENCES_NAME)

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {
    @Binds
    abstract fun bindDataStoreManager(dataStoreManagerImpl: DataStoreManagerImpl): PreferencesStorage
}