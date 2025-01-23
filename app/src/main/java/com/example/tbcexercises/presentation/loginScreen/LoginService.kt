package com.example.tbcexercises.presentation.loginScreen

import com.example.tbcexercises.data.model.request.AuthRequest
import com.example.tbcexercises.data.model.response.LoginResponse
import com.example.tbcexercises.data.remote.RetrofitInstance
import retrofit2.Response

object LoginService {
    suspend fun login(authRequest: AuthRequest): Response<LoginResponse> {
       return RetrofitInstance.api.login(authRequest)
    }
}