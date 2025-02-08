package com.example.tbcexercises.data.remote.apis

import com.example.tbcexercises.data.remote.response.UserListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface UserApi {

    @GET("users")
    suspend fun fetchUsers(@Query("page") pageNumber: Int): Response<UserListResponse>
}
