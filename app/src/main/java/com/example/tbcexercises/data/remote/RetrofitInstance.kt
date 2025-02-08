package com.example.tbcexercises.data.remote

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


object RetrofitInstance {
    private const val BASE_URL = "https://fakestoreapi.com/"

    private val json = Json { ignoreUnknownKeys = true }
    val api: ProductApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ProductApi::class.java)
    }
}