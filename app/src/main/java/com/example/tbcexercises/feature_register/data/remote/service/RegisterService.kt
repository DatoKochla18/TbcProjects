package com.example.tbcexercises.feature_register.data.remote.service

import com.example.tbcexercises.core.data.remote.request.AuthRequest
import com.example.tbcexercises.feature_register.data.remote.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("register")
    suspend fun register(@Body registerRequest: AuthRequest): Response<RegisterResponse>

}