package com.example.tbcexercises.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.use_case.GetConnectivityUseCase
import com.example.tbcexercises.domain.use_case.GetImagesUseCase
import com.example.tbcexercises.domain.use_case.SearchImagesUseCase
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.presentation.base.BaseViewModel
import com.example.tbcexercises.presentation.mapper.asStringResource
import com.example.tbcexercises.presentation.mapper.toPresentation
import com.example.tbcexercises.presentation.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    private val searchImageUseCase: SearchImagesUseCase,
    private val getConnectivityUseCase: GetConnectivityUseCase,
) :
    BaseViewModel<HomeViewModel.HomeUiState, HomeViewModel.HomeEvents, HomeViewModel.HomeSideEffect>(
        HomeUiState()
    ) {

    private var searchJob: Job? = null


    init {
        viewModelScope.launch {
            getConnectivityUseCase().collect { isConnected ->
                setState { copy(isConnected = isConnected) }

                if (isConnected && state.value.images.isEmpty() && !state.value.isLoading) {
                    getImages()
                }
            }
        }
    }


    override fun handleEvent(event: HomeEvents) {
        when (event) {
            is HomeEvents.ClickedOnImage -> {
                sendEffect(HomeSideEffect.NavigateToDetailScreen(event.hex))
            }

            HomeEvents.GetImages -> getImages()
            is HomeEvents.SearchImages -> searchImages(event.title)
        }
    }

    private fun searchImages(query: String) {

        if (query.isBlank()) {
            getImages()
            return
        }

        if (!state.value.isConnected) {
            return
        }

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(400)

            setState { copy(isLoading = true, images = emptyList()) }


            if (!isActive) return@launch

            when (val result = searchImageUseCase(query)) {
                is Resource.Error -> {
                    if (isActive) {
                        setState {
                            copy(isLoading = false, images = emptyList())
                        }
                        sendEffect(HomeSideEffect.ShowError(result.error.asStringResource()))
                    }
                }

                is Resource.Success -> {
                    if (isActive) {
                        val list = result.data.map { it.toPresentation() }
                        setState {
                            copy(isLoading = false, images = list)
                        }
                    }
                }
            }
        }
    }

    private fun getImages() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }

            when (val result = getImagesUseCase()) {
                is Resource.Error -> {
                    setState { copy(isLoading = false) }
                    sendEffect(HomeSideEffect.ShowError(result.error.asStringResource()))
                }

                is Resource.Success -> {
                    setState {
                        copy(
                            isLoading = false,
                            images = result.data.map { it.toPresentation() })
                    }
                }
            }
        }
    }


    data class HomeUiState(
        val isLoading: Boolean = false,
        val isConnected: Boolean = true,
        val images: List<Image> = emptyList(),
    )

    sealed interface HomeEvents {
        object GetImages : HomeEvents

        data class SearchImages(val title: String) : HomeEvents

        data class ClickedOnImage(val hex: String) : HomeEvents
    }

    sealed interface HomeSideEffect {
        data class NavigateToDetailScreen(val hex: String) : HomeSideEffect

        data class ShowError(val message: Int) : HomeSideEffect
    }
}