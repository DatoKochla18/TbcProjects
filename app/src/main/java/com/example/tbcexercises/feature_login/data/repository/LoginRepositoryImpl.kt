package com.example.tbcexercises.feature_login.data.repository

import com.example.tbcexercises.core.data.remote.request.AuthRequest
import com.example.tbcexercises.core.domain.manager.UserSessionManager
import com.example.tbcexercises.core.domain.model.Profile
import com.example.tbcexercises.core.domain.util.Result
import com.example.tbcexercises.core.domain.util.error.NetworkError
import com.example.tbcexercises.core.presentation.util.PreferenceKeys.TOKEN_KEY
import com.example.tbcexercises.core.utils.ApiHelper
import com.example.tbcexercises.core.utils.mapData
import com.example.tbcexercises.feature_login.data.mapper.toProfile
import com.example.tbcexercises.feature_login.data.remote.service.LoginApi
import com.example.tbcexercises.feature_login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val loginApi: LoginApi,
    private val userSessionManager: UserSessionManager,
) : LoginRepository {
    override suspend fun login(email: String, password: String): Result<Profile, NetworkError> {
        val result = apiHelper.handleNetworkRequestAsSuspend {
            loginApi.login(
                AuthRequest(
                    email = email,
                    password = password
                )
            )
        }
        if (result is Result.Success) {
            userSessionManager.saveValue(TOKEN_KEY, result.data.token)
        }

        return result.mapData { it.toProfile() }
    }

}