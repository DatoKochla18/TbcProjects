package com.example.tbcexercises.domain.repository

import com.example.tbcexercises.data.remote.response.RegisterResponse
import com.example.tbcexercises.utils.common.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    fun register(email: String, password: String): Flow<Resource<RegisterResponse>>

}