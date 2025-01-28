package com.example.tbcexercises.data.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://reqres.in/api/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    val api: AuthApi by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(AuthApi::class.java)
    }

}