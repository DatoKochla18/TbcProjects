package com.example.tbcexercises.data.remote.service

import com.example.tbcexercises.data.remote.response.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface CategoryService {

    @GET("499e0ffd-db69-4955-8d86-86ee60755b9c")
    suspend fun getCategories(): Response<List<CategoryResponse>>
}