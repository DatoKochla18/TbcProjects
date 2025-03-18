package com.example.tbcexercises.data.remote.service

import com.example.tbcexercises.data.remote.response.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface CategoryService {

    @GET("5809d42b-8d66-46ee-8bc4-4dae26fd5258")
    suspend fun getCategories(): Response<List<CategoryResponse>>
}