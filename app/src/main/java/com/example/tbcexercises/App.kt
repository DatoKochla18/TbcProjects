package com.example.tbcexercises

import android.app.Application
import android.content.Context
import com.example.tbcexercises.data.local.room.AppDatabase
import com.example.tbcexercises.data.remote.RetrofitInstance
import com.example.tbcexercises.data.repository.AuthRepositoryImpl
import com.example.tbcexercises.data.repository.UserRepositoryImpl

class App : Application() {
    val authRepository by lazy { AuthRepositoryImpl(RetrofitInstance.authApi) }
    private val appDatabase by lazy { AppDatabase.getInstance(this) }

    val userRepository by lazy { UserRepositoryImpl(appDatabase, RetrofitInstance.userApi) }

    override fun onCreate() {
        CONTEXT = applicationContext
        super.onCreate()

    }

    companion object {
        var CONTEXT: Context? = null

    }
}