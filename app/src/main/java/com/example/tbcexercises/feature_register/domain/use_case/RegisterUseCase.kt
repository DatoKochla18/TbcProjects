package com.example.tbcexercises.feature_register.domain.use_case

import com.example.tbcexercises.core.domain.util.Resource
import com.example.tbcexercises.core.domain.util.error.NetworkError
import com.example.tbcexercises.feature_register.domain.model.GetProfileRegistered
import com.example.tbcexercises.feature_register.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val registerRepository: RegisterRepository) {
    operator fun invoke(email: String, password: String): Flow<Resource<GetProfileRegistered, NetworkError>> {
        return registerRepository.register(email = email, password = password)
    }
}