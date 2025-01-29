package com.example.tbcexercises

import android.app.Application
import android.content.Context

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
    }

    companion object {
        var CONTEXT: Context? = null
    }
}