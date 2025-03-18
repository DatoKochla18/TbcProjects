package com.example.tbcexercises.presentation.search


sealed class SearchSideEffects {
    data class ShowError(val error: String) : SearchSideEffects()
}