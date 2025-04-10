package com.example.tbcexercises.feature_login.domain.use_case

import com.example.tbcexercises.core.domain.util.Resource
import com.example.tbcexercises.core.domain.util.error.NetworkError
import com.example.tbcexercises.feature_login.domain.model.GetProfileLogin
import com.example.tbcexercises.feature_login.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) {
    operator fun invoke(email: String, password: String): Flow<Resource<GetProfileLogin, NetworkError>> {
        return flow {
            val result = loginRepository.login(email = email, password = password)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}