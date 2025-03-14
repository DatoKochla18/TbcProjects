package com.example.tbcexercises.feature_login.domain.repository

import com.example.tbcexercises.core.domain.model.Profile
import com.example.tbcexercises.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(email: String, password: String): Flow<Resource<Profile>>

}