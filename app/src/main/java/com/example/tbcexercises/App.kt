package com.example.tbcexercises

import android.app.Application
import android.content.Context
import com.example.tbcexercises.data.remote.RetrofitInstance
import com.example.tbcexercises.data.repository.AuthRepositoryImpl

class App : Application() {
    val authRepository by lazy { AuthRepositoryImpl(RetrofitInstance.authApi) }

    override fun onCreate() {
        CONTEXT = applicationContext
        super.onCreate()

    }

    companion object {
        var CONTEXT: Context? = null

    }
}