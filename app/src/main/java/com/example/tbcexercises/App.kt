package com.example.tbcexercises

import android.app.Application
import com.example.tbcexercises.data.local.AppDatabase
import com.example.tbcexercises.data.remote.RetrofitInstance
import com.example.tbcexercises.data.repository.UserRepository

class App : Application() {

    private val appDatabase by lazy { AppDatabase.getInstance(this) }

    val userRepository by lazy { UserRepository(appDatabase, RetrofitInstance.userApi) }

}