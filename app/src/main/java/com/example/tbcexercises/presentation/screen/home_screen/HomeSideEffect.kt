package com.example.tbcexercises.presentation.screen.home_screen

sealed interface HomeSideEffect {
    data class ShowError(val message:Int):HomeSideEffect
}