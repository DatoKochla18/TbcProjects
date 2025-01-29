package com.example.tbcexercises.presentation.readScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.R
import com.example.tbcexercises.data.datastore.ProtoDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReadScreenViewModel : ViewModel() {
    private val _readUserState = MutableStateFlow(ReadUiState())
    val readUserState: StateFlow<ReadUiState> = _readUserState

    fun readUser(email: String) {
        _readUserState.update { it.copy(isLoading = true, isSuccessful = null, error = null) }
        viewModelScope.launch {
            try {
                ProtoDataStore.readUser(email).collectLatest { user ->
                    _readUserState.update {
                        it.copy(
                            isLoading = false,
                            isSuccessful = user,
                            error = null
                        )
                    }
                }


            } catch (e: Exception) {
                _readUserState.update {
                    it.copy(
                        isLoading = false,
                        isSuccessful = null,
                        error = R.string.something_went_wrong_try_again
                    )
                }

            }

        }
    }
}