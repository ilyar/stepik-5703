package org.stepik.a5703.myapplication

import android.app.Application
import android.util.Log

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("live", "App.onCreate")
    }
}
