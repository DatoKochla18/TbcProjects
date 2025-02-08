package com.example.tbcexercises.domain.repository

import com.example.tbcexercises.common.Resource
import com.example.tbcexercises.data.remote.response.LoginResponse
import com.example.tbcexercises.data.remote.response.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun login(email: String, password: String): Flow<Resource<LoginResponse>>

    fun register(email: String, password: String): Flow<Resource<RegisterResponse>>

}