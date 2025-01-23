package com.example.tbcexercises.data.dataStore

import kotlinx.serialization.Serializable

@Serializable
data class UserSession(val rememberMe: Boolean = false, val email: String = "")
