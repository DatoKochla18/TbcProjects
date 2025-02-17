package com.example.tbcexercises.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserSessionRepository {

    suspend fun setSession(rememberMe: Boolean, email: String)


    fun getEmailFlow(): Flow<String?>

    fun getRememberMeFlow(): Flow<Boolean>
}