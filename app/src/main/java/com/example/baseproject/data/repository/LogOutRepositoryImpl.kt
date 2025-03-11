package com.example.baseproject.data.repository

import com.example.baseproject.data.local.datastore.DataStoreManager
import com.example.baseproject.data.local.datastore.DataStorePreferenceKeys
import com.example.baseproject.domain.repository.LogOutRepository
import javax.inject.Inject

class LogOutRepositoryImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager
): LogOutRepository {
    override suspend fun logOut() {
        try {
            dataStoreManager.removeByKey(DataStorePreferenceKeys.TOKEN_KEY)
            dataStoreManager.removeByKey(DataStorePreferenceKeys.EMAIL_KEY)
        } catch (e: Exception) {
            throw RuntimeException("Failed to save login data: ${e.message}")
        }
    }
}