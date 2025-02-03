package com.example.tbcexercises

import android.app.Application
import com.example.tbcexercises.data.local.AppDatabase
import com.example.tbcexercises.data.remote.RetrofitInstance
import com.example.tbcexercises.data.repository.UserRepository

class App : Application() {

    private val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { UserRepository(database.userDao(), RetrofitInstance.api) }
}