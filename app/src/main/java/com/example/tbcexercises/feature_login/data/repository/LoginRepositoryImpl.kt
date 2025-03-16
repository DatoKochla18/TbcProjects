package com.example.tbcexercises.feature_login.data.repository

import com.example.tbcexercises.core.data.remote.request.AuthRequest
import com.example.tbcexercises.core.domain.model.Profile
import com.example.tbcexercises.core.domain.util.Result
import com.example.tbcexercises.core.domain.util.error.NetworkError
import com.example.tbcexercises.core.utils.handleNetworkRequest
import com.example.tbcexercises.core.utils.mapData
import com.example.tbcexercises.feature_login.data.mapper.toProfile
import com.example.tbcexercises.feature_login.data.remote.service.LoginApi
import com.example.tbcexercises.feature_login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginApi: LoginApi) : LoginRepository {
    override fun login(email: String, password: String): Flow<Result<Profile,NetworkError>> {
        return handleNetworkRequest(apiCall = {
            loginApi.login(
                AuthRequest(
                    email = email,
                    password = password
                )
            )
        }).map { result -> result.mapData { it.toProfile() } }
    }
}