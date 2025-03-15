package com.example.tbcexercises.feature_register.data.repository

import com.example.tbcexercises.core.data.remote.request.AuthRequest
import com.example.tbcexercises.core.domain.model.Profile
import com.example.tbcexercises.core.domain.util.Resource
import com.example.tbcexercises.core.utils.handleNetworkRequest
import com.example.tbcexercises.core.utils.mapData
import com.example.tbcexercises.feature_register.data.mapper.toProfile
import com.example.tbcexercises.feature_register.data.remote.service.RegisterApi
import com.example.tbcexercises.feature_register.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val registerApi: RegisterApi) :
    RegisterRepository {
    override fun register(email: String, password: String): Flow<Resource<Profile>> {
        return handleNetworkRequest(
            apiCall = {
                registerApi.register(
                    AuthRequest(
                        email = email,
                        password = password
                    )
                )
            }
        ).map { resource ->
            resource.mapData { it.toProfile() }
        }
    }
}
