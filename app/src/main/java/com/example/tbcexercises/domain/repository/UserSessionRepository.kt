package com.example.tbcexercises.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserSessionRepository {

    suspend fun setSession(rememberMe: Boolean, email: String)

    val emailFlow: Flow<String?>

    val rememberMeFlow: Flow<Boolean>
}