package com.example.tbcexercises.feature_register.domain.use_case

import com.example.tbcexercises.core.domain.model.Profile
import com.example.tbcexercises.core.utils.Resource
import com.example.tbcexercises.feature_register.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val registerRepository: RegisterRepository) {
    operator fun invoke(email: String, password: String): Flow<Resource<Profile>> {
        return registerRepository.register(email = email, password = password)
    }
}