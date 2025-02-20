package com.example.tbcexercises.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    suspend fun setSession(language: String?, rememberMe: Boolean?)


    fun getLanguageFlow(): Flow<String>

    fun getRememberMe(): Flow<Boolean>

}