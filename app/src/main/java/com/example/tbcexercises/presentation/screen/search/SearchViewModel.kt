package com.example.tbcexercises.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.use_case.SearchCategoriesUseCase
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.presentation.mapper.toPresentation
import com.example.tbcexercises.presentation.util.Constants.TIME_BEFORE_FIRING_REQUEST
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCategoriesUseCase: SearchCategoriesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchUiState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<SearchSideEffects>()
    val uiEvent = _uiEvent.receiveAsFlow()


    private var searchJob: Job? = null

    fun onEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.SearchCategories -> getCategories(event.query)
        }
    }

    private fun getCategories(query: String) {


        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(TIME_BEFORE_FIRING_REQUEST)
            _state.update { it.copy(isLoading = true, categories = emptyList()) }
            searchCategoriesUseCase(query).collectLatest { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        _uiEvent.send(SearchSideEffects.ShowError(resource.message))
                    }

                    is Resource.Success -> _state.update { searchUiState ->
                        searchUiState.copy(
                            categories = resource.data
                                .map {
                                    it.toPresentation()
                                },
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }
}