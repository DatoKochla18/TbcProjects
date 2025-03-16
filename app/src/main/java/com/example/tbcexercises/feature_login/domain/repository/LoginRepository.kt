package com.example.tbcexercises.feature_login.domain.repository

import com.example.tbcexercises.core.domain.model.Profile
import com.example.tbcexercises.core.domain.util.Result
import com.example.tbcexercises.core.domain.util.error.NetworkError
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(email: String, password: String): Flow<Result<Profile, NetworkError>>

}