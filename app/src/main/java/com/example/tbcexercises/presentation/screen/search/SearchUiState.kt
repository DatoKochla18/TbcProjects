package com.example.tbcexercises.presentation.screen.search

import com.example.tbcexercises.presentation.model.Category

data class SearchUiState(
    val categories: List<Category> = listOf(),
    val isLoading: Boolean = false,
)