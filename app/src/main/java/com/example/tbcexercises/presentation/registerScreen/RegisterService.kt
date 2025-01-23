package com.example.tbcexercises.presentation.registerScreen

import com.example.tbcexercises.data.model.request.AuthRequest
import com.example.tbcexercises.data.model.response.RegisterResponse
import com.example.tbcexercises.data.remote.RetrofitInstance
import retrofit2.Response

object RegisterService {
    suspend fun register(authRequest: AuthRequest): Response<RegisterResponse> {
        return RetrofitInstance.api.register(authRequest)
    }
}