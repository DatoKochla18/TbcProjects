package com.example.tbcexercises.presentation.screen.search


sealed class SearchSideEffects {
    data class ShowError(val error: String) : SearchSideEffects()
}