package com.example.tbcexercises.data.remote


import com.example.tbcexercises.data.model.request.AuthRequest
import com.example.tbcexercises.data.model.response.LoginResponse
import com.example.tbcexercises.data.model.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthApi {

    @POST("login")
    suspend fun login(@Body loginRequest: AuthRequest): Response<LoginResponse>

    @POST("register")
    suspend fun register(@Body registerRequest: AuthRequest): Response<RegisterResponse>

}