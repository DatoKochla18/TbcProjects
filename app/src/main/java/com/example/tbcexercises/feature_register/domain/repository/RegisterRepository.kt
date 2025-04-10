package com.example.tbcexercises.feature_register.domain.repository

import com.example.tbcexercises.core.domain.util.Resource
import com.example.tbcexercises.core.domain.util.error.NetworkError
import com.example.tbcexercises.feature_register.domain.model.GetProfileRegistered
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    fun register(email: String, password: String): Flow<Resource<GetProfileRegistered, NetworkError>>
}