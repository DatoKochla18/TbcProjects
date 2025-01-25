package com.example.tbcexercises.presentation.registerScreen

import com.example.tbcexercises.data.model.response.RegisterResponse

data class RegisterState(
    val loading: Boolean = false,
    val success: RegisterResponse? = null,
    val error: Int? = null
)
