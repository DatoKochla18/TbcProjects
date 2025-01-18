package com.example.tbcexercises.presentation.registerScreen

import com.example.tbcexercises.data.request.AuthRequest
import com.example.tbcexercises.data.response.RegisterResponse
import com.example.tbcexercises.data.source.remote.RetrofitInstance
import retrofit2.Response

class RegisterService(authRequest: AuthRequest) {
    suspend operator fun invoke(authRequest: AuthRequest): Response<RegisterResponse> {
        return RetrofitInstance.api.register(authRequest)
    }
}