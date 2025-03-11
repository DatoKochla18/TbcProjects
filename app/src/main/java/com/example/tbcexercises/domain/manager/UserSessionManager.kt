package com.example.tbcexercises.domain.manager

import kotlinx.coroutines.flow.Flow

interface UserSessionManager {

    suspend fun setSession(rememberMe: Boolean, email: String)


    fun getEmailFlow(): Flow<String?>

    fun getRememberMeFlow(): Flow<Boolean>
}