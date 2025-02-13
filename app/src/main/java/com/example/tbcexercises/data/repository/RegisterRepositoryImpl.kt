package com.example.tbcexercises.data.repository

import com.example.tbcexercises.data.remote.apis.AuthApi
import com.example.tbcexercises.data.remote.request.AuthRequest
import com.example.tbcexercises.data.remote.response.RegisterResponse
import com.example.tbcexercises.domain.repository.RegisterRepository
import com.example.tbcexercises.utils.common.Resource
import com.example.tbcexercises.utils.common.handleNetworkRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val authApi: AuthApi) :
    RegisterRepository {
    override fun register(email: String, password: String): Flow<Resource<RegisterResponse>> {
        return handleNetworkRequest {
            authApi.register(
                AuthRequest(
                    email = email,
                    password = password
                )
            )
        }
    }
}