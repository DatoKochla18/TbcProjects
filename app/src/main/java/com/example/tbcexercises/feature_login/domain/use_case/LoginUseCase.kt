package com.example.tbcexercises.feature_login.domain.use_case

import com.example.tbcexercises.core.domain.model.Profile
import com.example.tbcexercises.core.utils.Resource
import com.example.tbcexercises.feature_login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    operator fun invoke(email: String, password: String): Flow<Resource<Profile>> {
        return loginRepository.login(email = email, password = password)
    }
}