package com.example.tbcexercises.presentation.search

sealed class CategoryUiEvent {
    object LoadCategories : CategoryUiEvent()
    data class SearchCategories(val query: String) : CategoryUiEvent()
}