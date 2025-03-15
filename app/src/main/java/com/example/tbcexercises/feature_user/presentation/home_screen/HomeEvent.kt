package com.example.tbcexercises.feature_user.presentation.home_screen

sealed interface HomeEvent {
    data class ShowError(val message: String) : HomeEvent
}