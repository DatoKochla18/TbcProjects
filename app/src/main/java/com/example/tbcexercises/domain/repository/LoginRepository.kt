package com.example.tbcexercises.domain.repository

import com.example.tbcexercises.data.remote.response.LoginResponse
import com.example.tbcexercises.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(email: String, password: String): Flow<Resource<LoginResponse>>

}