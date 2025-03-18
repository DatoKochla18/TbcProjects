package com.example.tbcexercises.presentation.screen.search

sealed class SearchUiEvent {
    data class SearchCategories(val query: String) : SearchUiEvent()
}