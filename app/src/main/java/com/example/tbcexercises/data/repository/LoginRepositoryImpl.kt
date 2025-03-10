package com.example.tbcexercises.data.repository

import com.example.tbcexercises.data.remote.apis.LoginApi
import com.example.tbcexercises.data.remote.request.AuthRequest
import com.example.tbcexercises.data.remote.response.LoginResponse
import com.example.tbcexercises.domain.repository.LoginRepository
import com.example.tbcexercises.utils.Resource
import com.example.tbcexercises.utils.handleNetworkRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginApi: LoginApi) : LoginRepository {
    override fun login(email: String, password: String): Flow<Resource<LoginResponse>> {
        return handleNetworkRequest {
            loginApi.login(
                AuthRequest(
                    email = email,
                    password = password
                )
            )
        }
    }
}