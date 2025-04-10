package com.example.tbcexercises.feature_login.data.repository

import com.example.tbcexercises.core.data.remote.request.AuthRequest
import com.example.tbcexercises.core.data.remote.utils.ApiHelper
import com.example.tbcexercises.core.data.remote.utils.mapData
import com.example.tbcexercises.core.domain.manager.UserSessionManager
import com.example.tbcexercises.core.domain.util.PreferenceKeys.EMAIL_KEY
import com.example.tbcexercises.core.domain.util.PreferenceKeys.TOKEN_KEY
import com.example.tbcexercises.core.domain.util.Resource
import com.example.tbcexercises.core.domain.util.error.NetworkError
import com.example.tbcexercises.feature_login.data.mapper.toDomain
import com.example.tbcexercises.feature_login.data.remote.service.LoginService
import com.example.tbcexercises.feature_login.domain.model.GetProfileLogin
import com.example.tbcexercises.feature_login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val loginService: LoginService,
    private val userSessionManager: UserSessionManager,
) : LoginRepository {
    override suspend fun login(
        email: String,
        password: String,
    ): Resource<GetProfileLogin, NetworkError> {
        val result = apiHelper.handleNetworkRequestAsSuspend {
            loginService.login(
                AuthRequest(
                    email = email,
                    password = password
                )
            )
        }
        if (result is Resource.Success) {
            userSessionManager.saveValue(TOKEN_KEY, result.data.token)
            userSessionManager.saveValue(EMAIL_KEY, email)

        }

        return result.mapData { it.toDomain() }
    }

}