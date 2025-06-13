package com.example.tbcexercises.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.RootError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class BaseViewModel<STATE : Any, EFFECT : RootError>(initialState: STATE) : ViewModel() {
    protected val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<STATE> = _uiState.asStateFlow()

    protected val _sideEffect = MutableSharedFlow<EFFECT>()
    val sideEffect: SharedFlow<EFFECT> = _sideEffect.asSharedFlow()


    protected fun <T> launchData(
        loader: suspend () -> Resource<T, EFFECT>,
        onLoading: (Boolean) -> STATE,
        onSuccess: STATE.(T) -> STATE,
    ) {
        viewModelScope.launch {
            _uiState.update { onLoading(true) }

            when (val result = loader()) {
                is Resource.Success -> {
                    _uiState.update { onLoading(false) }
                    _uiState.update { it.onSuccess(result.data) }
                }

                is Resource.Error -> {
                    _sideEffect.emit(result.error)
                    _uiState.update { onLoading(false) }
                }
            }
        }
    }
}
