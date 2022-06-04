package com.example.expandabletoolbartest.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Instance = this
    }

    companion object {
        lateinit var Instance: App
    }

}