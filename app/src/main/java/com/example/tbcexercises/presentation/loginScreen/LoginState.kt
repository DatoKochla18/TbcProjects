package com.example.tbcexercises.presentation.loginScreen

import com.example.tbcexercises.data.model.response.LoginResponse

data class LoginState(
    val loading: Boolean = false,
    val success: LoginResponse? = null,
    val error: String? = null
)
