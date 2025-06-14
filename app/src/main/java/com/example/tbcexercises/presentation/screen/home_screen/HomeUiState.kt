package com.example.tbcexercises.presentation.screen.home_screen

import com.example.tbcexercises.presentation.model.Breed

data class HomeUiState(
    val isLoading: Boolean = false,
    val breeds: List<Breed> = emptyList(),
    val isInternet:Boolean = true
)