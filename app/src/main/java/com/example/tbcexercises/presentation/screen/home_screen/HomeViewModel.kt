package com.example.tbcexercises.presentation.screen.home_screen

import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.use_case.GetBreedUseCase
import com.example.tbcexercises.domain.use_case.GetConnectivity
import com.example.tbcexercises.domain.use_case.SearchBreedUseCase
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.presentation.base.BaseViewModel
import com.example.tbcexercises.presentation.mapper.asStringResource
import com.example.tbcexercises.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBreedUseCase: GetBreedUseCase,
    private val searchBreedUseCase: SearchBreedUseCase,
    private val getConnectivity: GetConnectivity,
) : BaseViewModel<HomeUiState, HomeEvent, HomeSideEffect>(HomeUiState()) {

    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            getConnectivity().collect { isConnected ->
                setState { copy(isInternet = isConnected) }

                if (isConnected && state.value.breeds.isEmpty() && !state.value.isLoading) {
                    getBreeds()
                }
            }
        }
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetBreeds -> getBreeds()
            is HomeEvent.SearchBreads -> searchBreadsWithDebounce(event.query)
            is HomeEvent.ClickedOnItem -> {
                sendEffect(HomeSideEffect.NavigateToDetail(event.name))
            }
        }
    }

    private fun getBreeds() {
        if (!state.value.isInternet) {
            return
        }


        setState { copy(isLoading = true, breeds = emptyList()) }
        viewModelScope.launch {
            when (val result = getBreedUseCase()) {
                is Resource.Error -> {
                    setState { copy(isLoading = false, breeds = emptyList()) }
                    sendEffect(HomeSideEffect.ShowError(result.error.asStringResource()))
                }

                is Resource.Success -> setState {
                    copy(
                        isLoading = false,
                        breeds = result.data.map { it.toPresentation() }
                    )
                }
            }
        }
    }

    private fun searchBreadsWithDebounce(query: String) {

        if (query.isBlank()) {
            getBreeds()
            return
        }

        if (!state.value.isInternet) {
            return
        }
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(400)

            setState { copy(isLoading = true, breeds = emptyList()) }


            if (!isActive) return@launch

            when (val result = searchBreedUseCase(query)) {
                is Resource.Error -> {
                    if (isActive) {
                        setState {
                            copy(isLoading = false, breeds = emptyList())
                        }
                        sendEffect(HomeSideEffect.ShowError(result.error.asStringResource()))
                    }
                }

                is Resource.Success -> {
                    if (isActive) {
                        val list = result.data.map { it.toPresentation() }
                        setState {
                            copy(isLoading = false, breeds = list)
                        }
                    }
                }
            }
        }
    }
}