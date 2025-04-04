package com.example.tbcexercises.feature_profile.presentation.profile_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.core.domain.use_case.GetValueFromLocalStorageUseCase
import com.example.tbcexercises.core.domain.use_case.SaveValueToLocalStorageUseCase
import com.example.tbcexercises.core.domain.util.PreferenceKeys.EMAIL_KEY
import com.example.tbcexercises.core.domain.util.PreferenceKeys.REMEMBER_ME_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getValueFromLocalStorageUseCase: GetValueFromLocalStorageUseCase,
    private val saveValueToLocalStorageUseCase: SaveValueToLocalStorageUseCase,
) : ViewModel() {

    var uiState by mutableStateOf(ProfileUiState(email = ""))
        private set

    private val emailFlow: Flow<String> = getValueFromLocalStorageUseCase(EMAIL_KEY, "")

    private val _sideEffect = Channel<ProfileSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        onEvent(ProfileEvent.GetEmail)
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.GetEmail -> getEmail()
            ProfileEvent.OnLogOutClicked -> onLogOutClick()
        }
    }

    private fun getEmail() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            emailFlow.collect { email ->
                uiState = uiState.copy(
                    isLoading = false,
                    email = email
                )
            }
        }
    }

    private fun onLogOutClick() {
        viewModelScope.launch {
            withContext(NonCancellable) {
                saveValueToLocalStorageUseCase(EMAIL_KEY, "")
                saveValueToLocalStorageUseCase(REMEMBER_ME_KEY, false)
                _sideEffect.send(ProfileSideEffect.OnSuccessfulLogout)
            }
        }
    }
}