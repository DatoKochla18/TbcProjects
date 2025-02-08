package com.example.tbcexercises

import android.app.Application
import com.example.tbcexercises.data.remote.RetrofitInstance
import com.example.tbcexercises.data.repository.ProductRepositoryImpl

class App : Application() {
    val repository by lazy { ProductRepositoryImpl(RetrofitInstance.api) }

}