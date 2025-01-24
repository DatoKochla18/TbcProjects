package com.example.tbcexercises.presentation.homeScreen


import com.example.tbcexercises.data.model.response.UserResponse
import com.example.tbcexercises.data.remote.RetrofitInstance
import retrofit2.Response

object HomeService {
    suspend fun fetchUsers(page: Int): Response<UserResponse> {
        return RetrofitInstance.api.fetchUsers(page)
    }
}