package com.example.tbcexercises.feature_user.data.remote.service

import com.example.tbcexercises.feature_user.data.remote.response.UserListResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface UserApi {

    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int): UserListResponse
}
