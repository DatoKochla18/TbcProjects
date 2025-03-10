package com.example.tbcexercises.domain.use_case

import com.example.tbcexercises.domain.model.User
import com.example.tbcexercises.domain.repository.LoginRepository
import com.example.tbcexercises.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    operator fun invoke(email: String, password: String): Flow<Resource<User>> {
        return loginRepository.login(email = email, password = password)
    }
}