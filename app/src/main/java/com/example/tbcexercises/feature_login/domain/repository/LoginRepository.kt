package com.example.tbcexercises.feature_login.domain.repository

import com.example.tbcexercises.core.domain.model.Profile
import com.example.tbcexercises.core.domain.util.Result
import com.example.tbcexercises.core.domain.util.error.NetworkError

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<Profile, NetworkError>
}