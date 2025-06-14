package com.example.tbcexercises.presentation.screen.home_screen

sealed interface HomeEvent {

    object GetBreeds : HomeEvent

    class SearchBreads(val query: String) : HomeEvent

    data class ClickedOnItem(val name:String):HomeEvent
}