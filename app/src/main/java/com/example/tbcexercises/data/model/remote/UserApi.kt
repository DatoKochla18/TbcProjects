package com.example.tbcexercises.data.model.remote


import retrofit2.http.GET

interface UserApi {

    @GET("f3f41821-7434-471f-9baa-ae3dee984e6d")
    suspend fun getUsers(): UserListResponse
}