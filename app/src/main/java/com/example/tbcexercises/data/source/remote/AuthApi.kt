package com.example.tbcexercises.data.source.remote

import com.example.tbcexercises.data.request.AuthRequest
import com.example.tbcexercises.data.response.LoginResponse
import com.example.tbcexercises.data.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthApi {

    @POST("login")
    suspend fun login(@Body loginRequest: AuthRequest): Response<LoginResponse>

    @POST("register")
    suspend fun register(@Body registerRequest: AuthRequest): Response<RegisterResponse>

}