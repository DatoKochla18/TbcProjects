package com.example.tbcexercises.data.repository

import com.example.tbcexercises.data.mappers.toUser
import com.example.tbcexercises.data.remote.apis.RegisterApi
import com.example.tbcexercises.data.remote.request.AuthRequest
import com.example.tbcexercises.data.remote.response.RegisterResponse
import com.example.tbcexercises.domain.model.User
import com.example.tbcexercises.domain.repository.RegisterRepository
import com.example.tbcexercises.utils.Resource
import com.example.tbcexercises.utils.handleNetworkRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val registerApi: RegisterApi) :
    RegisterRepository {
    override fun register(email: String, password: String): Flow<Resource<User>> {
        return handleNetworkRequest(
            apiCall = {
                registerApi.register(
                    AuthRequest(
                        email = email,
                        password = password
                    )
                )
            }, mapper = { it.toUser() }
        )
    }
}