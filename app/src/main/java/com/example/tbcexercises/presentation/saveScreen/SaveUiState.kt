package com.example.tbcexercises.presentation.saveScreen

data class SaveUiState(
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val error: Int? = null
)
