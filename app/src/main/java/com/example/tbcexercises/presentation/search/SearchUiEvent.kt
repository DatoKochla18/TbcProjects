package com.example.tbcexercises.presentation.search

sealed class SearchUiEvent {
    data class SearchCategories(val query: String) : SearchUiEvent()
}