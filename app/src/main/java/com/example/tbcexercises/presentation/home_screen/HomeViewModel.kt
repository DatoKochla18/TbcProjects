package com.example.tbcexercises.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.tbcexercises.presentation.worker.UploadWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val workManager: WorkManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiSideEffect = Channel<HomeUiSideEffect>()
    val uiSideEffect = _uiSideEffect.receiveAsFlow()

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.SendImage -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(isLoading = true) }
                }

                val uploadRequest = OneTimeWorkRequestBuilder<UploadWorker>()
                    .setInputData(workDataOf("file_path" to event.file.absolutePath))
                    .build()

                workManager.enqueue(uploadRequest)

                viewModelScope.launch {
                    workManager.getWorkInfoByIdFlow(uploadRequest.id)
                        .catch {
                            _uiState.update { it.copy(isLoading = false) }
                            _uiSideEffect.send(HomeUiSideEffect.FailedUpload)
                        }
                        .collect { workInfo ->
                            when (workInfo?.state) {
                                WorkInfo.State.SUCCEEDED -> {
                                    _uiState.update { it.copy(isLoading = false) }
                                    _uiSideEffect.send(HomeUiSideEffect.SuccessfulUpload)
                                }

                                WorkInfo.State.FAILED -> {
                                    _uiState.update { it.copy(isLoading = false) }
                                    _uiSideEffect.send(HomeUiSideEffect.FailedUpload)
                                }

                                else -> {}
                            }
                        }
                }
            }
        }
    }

}