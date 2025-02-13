package com.example.tbcexercises.data.repository

import com.example.tbcexercises.data.remote.apis.AuthApi
import com.example.tbcexercises.data.remote.request.AuthRequest
import com.example.tbcexercises.data.remote.response.LoginResponse
import com.example.tbcexercises.domain.repository.LoginRepository
import com.example.tbcexercises.utils.common.Resource
import com.example.tbcexercises.utils.common.handleNetworkRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val authApi: AuthApi) : LoginRepository {
    override fun login(email: String, password: String): Flow<Resource<LoginResponse>> {
        return handleNetworkRequest {
            authApi.login(
                AuthRequest(
                    email = email,
                    password = password
                )
            )
        }
    }
}