package com.example.tbcexercises.presentation.screen.detail

import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.use_case.GetImageUseCase
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.presentation.base.BaseViewModel
import com.example.tbcexercises.presentation.mapper.asStringResource
import com.example.tbcexercises.presentation.mapper.toPresentation
import com.example.tbcexercises.presentation.model.ImageDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getImageUseCase: GetImageUseCase) :
    BaseViewModel<DetailViewModel.DetailUiState, DetailViewModel.DetailEvent, DetailViewModel.DetailSideEffect>(
        DetailUiState()
    ) {


    data class DetailUiState(val isLoading: Boolean = false, val detailImage: ImageDetail? = null)

    sealed interface DetailEvent {
        data class GetImage(val hex: String) : DetailEvent
    }

    sealed interface DetailSideEffect {
        data class ShowError(val message: Int) : DetailSideEffect
    }

    override fun handleEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.GetImage -> {
                viewModelScope.launch {
                    setState { copy(isLoading = true) }

                    when (val result = getImageUseCase(event.hex)) {
                        is Resource.Error -> {
                            setState { copy(isLoading = false) }
                            sendEffect(DetailSideEffect.ShowError(result.error.asStringResource()))
                        }

                        is Resource.Success -> {
                            setState {
                                copy(
                                    isLoading = false,
                                    detailImage = result.data.map { it.toPresentation() }.first()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}