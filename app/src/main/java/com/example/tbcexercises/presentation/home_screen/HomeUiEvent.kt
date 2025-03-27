package com.example.tbcexercises.presentation.home_screen

import java.io.File

sealed class HomeUiEvent {
    data class SendImage(val file: File) : HomeUiEvent()
}