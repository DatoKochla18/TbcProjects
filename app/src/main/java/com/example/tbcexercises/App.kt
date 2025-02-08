package com.example.tbcexercises

import android.app.Application
import com.example.tbcexercises.data.remote.RetrofitInstance
import com.example.tbcexercises.data.repository.AuthRepositoryImpl

class App : Application() {
    val authRepository by lazy { AuthRepositoryImpl(RetrofitInstance.authApi) }

}