package com.example.tbcexercises.domain.use_case

import com.example.tbcexercises.domain.model.User
import com.example.tbcexercises.domain.repository.RegisterRepository
import com.example.tbcexercises.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val registerRepository: RegisterRepository) {
    operator fun invoke(email: String, password: String): Flow<Resource<User>> {
        return registerRepository.register(email = email, password = password)
    }
}