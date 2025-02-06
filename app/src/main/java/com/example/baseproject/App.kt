package com.example.baseproject

import android.app.Application
import android.content.Context

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val context = this.applicationContext
    }

    companion object {
        var context = App().applicationContext
    }
}