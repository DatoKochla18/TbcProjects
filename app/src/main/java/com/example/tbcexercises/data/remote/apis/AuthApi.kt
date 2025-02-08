package com.example.tbcexercises.data.remote.apis


import com.example.tbcexercises.data.remote.request.AuthRequest
import com.example.tbcexercises.data.remote.response.LoginResponse
import com.example.tbcexercises.data.remote.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthApi {

    @POST("login")
    suspend fun login(@Body loginRequest: AuthRequest): Response<LoginResponse>

    @POST("register")
    suspend fun register(@Body registerRequest: AuthRequest): Response<RegisterResponse>

}