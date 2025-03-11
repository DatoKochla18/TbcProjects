package com.example.tbcexercises.feature_login.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(val token: String)

