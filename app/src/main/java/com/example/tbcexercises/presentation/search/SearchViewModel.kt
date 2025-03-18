package com.example.tbcexercises.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.use_case.SearchCategoriesUseCase
import com.example.tbcexercises.presentation.mapper.toPresentation
import com.example.tbcexercises.presentation.model.Category
import com.example.tbcexercises.utils.Resource
import com.example.tbcexercises.utils.mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCategoriesUseCase: SearchCategoriesUseCase,
) : ViewModel() {

    // Holds the current search query. Start with empty string.
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Expose UI state as a Flow of Resource<List<GetCategories>>
    val categoriesState: StateFlow<Resource<List<Category>>> = _searchQuery
        .debounce(300) // wait 300ms for user to finish typing
        .distinctUntilChanged()
        .flatMapLatest { query ->
            // Every time the query changes, fire a network request.
            searchCategoriesUseCase(query).map { it.mapper { it.map { it.toPresentation() } } }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, Resource.Loading)

    // Call this whenever the search text changes in your UI.
    fun onSearchTextChanged(query: String) {
        _searchQuery.value = query
    }
}