package com.example.tbcexercises.presentation.home_screen

sealed interface HomeUiSideEffect {
    data object SuccessfulUpload:HomeUiSideEffect
    data object FailedUpload:HomeUiSideEffect

}