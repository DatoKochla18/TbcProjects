package com.example.tbcexercises.data.network

import com.example.tbcexercises.data.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApi {

    @GET("users")
    suspend fun fetchUsers(@Query("page") page: Int): Response<UserResponse>
}