package com.example.tbcexercises.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<
        S : Any,
        E : Any,
        F : Any
        >(initialState: S) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<F>()
    val effects = _effects.asSharedFlow()

    fun process(event: E) {
        handleEvent(event)
    }

    protected abstract fun handleEvent(event: E)


    protected fun setState(reducer: S.() -> S) {
        _state.update { current ->
            current.reducer()
        }
    }


    protected fun sendEffect(effect: F) {
        viewModelScope.launch {
            _effects.emit(effect)
        }
    }
}