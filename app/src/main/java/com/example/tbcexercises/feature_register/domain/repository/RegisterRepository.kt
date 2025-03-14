package com.example.tbcexercises.feature_register.domain.repository

import com.example.tbcexercises.core.domain.model.Profile
import com.example.tbcexercises.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    fun register(email: String, password: String): Flow<Resource<Profile>>

}