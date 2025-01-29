package com.example.tbcexercises.presentation.readScreen

import com.example.tbcexercises.User

data class ReadUiState(
    val isLoading: Boolean = false,
    val isSuccessful: User? = null,
    val error: Int? = null
)


