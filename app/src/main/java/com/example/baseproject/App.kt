package com.example.baseproject

import android.app.Application
import com.example.baseproject.datastore.UserDataPreferencesRepository

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        UserDataPreferencesRepository.init(this)
    }
}