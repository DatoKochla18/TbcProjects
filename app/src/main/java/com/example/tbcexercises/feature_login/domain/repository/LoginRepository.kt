package com.example.tbcexercises.feature_login.domain.repository

import com.example.tbcexercises.core.domain.util.Result
import com.example.tbcexercises.core.domain.util.error.NetworkError
import com.example.tbcexercises.feature_login.domain.model.GetProfileLogin

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<GetProfileLogin, NetworkError>
}