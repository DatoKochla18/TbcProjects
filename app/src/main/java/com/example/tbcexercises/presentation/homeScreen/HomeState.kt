package com.example.tbcexercises.presentation.homeScreen

import com.example.tbcexercises.data.model.response.UserResponse

data class HomeState(
    val loading: Boolean = false,
    val success: UserResponse? = null,
    val error: String? = null
)
