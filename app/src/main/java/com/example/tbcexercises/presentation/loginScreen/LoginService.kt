package com.example.tbcexercises.presentation.loginScreen

import com.example.tbcexercises.data.request.AuthRequest
import com.example.tbcexercises.data.response.LoginResponse
import com.example.tbcexercises.data.source.remote.RetrofitInstance
import retrofit2.Response

class LoginService(authRequest: AuthRequest) {
    suspend operator fun invoke(authRequest: AuthRequest): Response<LoginResponse> {
       return RetrofitInstance.api.login(authRequest)
    }
}