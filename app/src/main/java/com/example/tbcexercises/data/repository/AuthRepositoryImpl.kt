package com.example.tbcexercises.data.repository

import com.example.tbcexercises.utils.common.Resource
import com.example.tbcexercises.utils.common.handleNetworkRequest
import com.example.tbcexercises.data.remote.apis.AuthApi
import com.example.tbcexercises.data.remote.request.AuthRequest
import com.example.tbcexercises.data.remote.response.LoginResponse
import com.example.tbcexercises.data.remote.response.RegisterResponse
import com.example.tbcexercises.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(private val authApi: AuthApi) : AuthRepository {
    override  fun login(email: String, password: String): Flow<Resource<LoginResponse>> {
        return  handleNetworkRequest { authApi.login(AuthRequest(email = email,password = password)) }
    }

    override  fun register(email: String, password: String):  Flow<Resource<RegisterResponse>>  {
        return  handleNetworkRequest { authApi.register(AuthRequest(email = email,password = password)) }
    }
}