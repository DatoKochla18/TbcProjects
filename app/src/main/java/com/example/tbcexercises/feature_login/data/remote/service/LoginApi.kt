package com.example.tbcexercises.feature_login.data.remote.service

import com.example.tbcexercises.core.data.remote.request.AuthRequest
import com.example.tbcexercises.feature_login.data.remote.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("login")
    suspend fun login(@Body loginRequest: AuthRequest): Response<LoginResponse>

}