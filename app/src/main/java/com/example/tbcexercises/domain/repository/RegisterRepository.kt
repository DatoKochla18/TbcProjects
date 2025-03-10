package com.example.tbcexercises.domain.repository

import com.example.tbcexercises.domain.model.User
import com.example.tbcexercises.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    fun register(email: String, password: String): Flow<Resource<User>>

}