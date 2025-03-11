package com.example.tbcexercises.feature_login.data.repository

import com.example.tbcexercises.core.data.remote.request.AuthRequest
import com.example.tbcexercises.core.domain.model.Profile
import com.example.tbcexercises.core.utils.Resource
import com.example.tbcexercises.core.utils.handleNetworkRequest
import com.example.tbcexercises.feature_login.data.mapper.toProfile
import com.example.tbcexercises.feature_login.data.remote.service.LoginApi
import com.example.tbcexercises.feature_login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginApi: LoginApi) : LoginRepository {
    override fun login(email: String, password: String): Flow<Resource<Profile>> {
        return handleNetworkRequest(apiCall = {
            loginApi.login(
                AuthRequest(
                    email = email,
                    password = password
                )
            )
        }, mapper = { it.toProfile() })
    }
}