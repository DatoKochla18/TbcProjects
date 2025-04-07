package com.example.tbcexercises.feature_register.presentation.register_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.core.domain.util.Result
import com.example.tbcexercises.core.presentation.extension.asStringResource
import com.example.tbcexercises.feature_register.domain.use_case.RegisterUseCaseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCaseWrapper: RegisterUseCaseWrapper,
) :
    ViewModel() {

    var uiState by mutableStateOf(RegisterUiState())
        private set

    private val _uiEventChannel = Channel<RegisterSideEffect>()
    val uiEvents = _uiEventChannel.receiveAsFlow()

    init {
        snapshotFlow { uiState.email }
            .drop(1)
            .map { registerUseCaseWrapper.validateEmailUseCase(it) }
            .onEach { result ->
                uiState = when (result) {
                    is Result.Error -> uiState.copy(emailError = result.error)
                    is Result.Success -> uiState.copy(
                        emailError = null,
                        isEmailValid = true,
                        isValidForm = uiState.isPasswordValid && uiState.isRepeatedPasswordValid
                    )
                }
            }
            .launchIn(viewModelScope)
        snapshotFlow { uiState.password }
            .drop(1)
            .map { registerUseCaseWrapper.validatePasswordUseCase(it) }
            .onEach { result ->
                uiState = when (result) {
                    is Result.Error -> uiState.copy(passwordError = result.error)
                    is Result.Success -> uiState.copy(
                        passwordError = null,
                        isPasswordValid = true,
                        isValidForm = uiState.isEmailValid && uiState.isRepeatedPasswordValid
                    )
                }
            }
            .launchIn(viewModelScope)
        snapshotFlow { uiState.repeatPassword }
            .drop(1)
            .map {
                registerUseCaseWrapper.validateRepeatPasswordUseCase(
                    password = uiState.password,
                    repeatedPassword = it
                )
            }
            .onEach { result ->
                uiState = when (result) {
                    is Result.Error -> uiState.copy(repeatedPasswordError = result.error)
                    is Result.Success -> uiState.copy(
                        repeatedPasswordError = null,
                        isRepeatedPasswordValid = true,
                        isValidForm = uiState.isPasswordValid && uiState.isEmailValid
                    )
                }
            }
            .launchIn(viewModelScope)
    }


    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.Register -> register(
                event.email,
                event.password
            )

            is RegisterEvent.OnEmailChanged -> {
                uiState = uiState.copy(email = event.email)
            }

            is RegisterEvent.OnPasswordChanged -> {
                uiState = uiState.copy(password = event.password)
            }

            is RegisterEvent.OnRepeatedPasswordChanged -> {
                uiState = uiState.copy(repeatPassword = event.repeatedPassword)
            }

            RegisterEvent.OnShowPasswordChanged -> {
                uiState = uiState.copy(showPassword = !uiState.showPassword)
            }
        }
    }

    private fun register(email: String, password: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            registerUseCaseWrapper.registerUseCase(email, password).collect { result ->
                when (result) {
                    is Result.Error -> {
                        uiState = uiState.copy(
                            isLoading = false,
                        )
                        _uiEventChannel.send(RegisterSideEffect.ShowError(result.error.asStringResource()))
                    }

                    is Result.Success -> {
                        _uiEventChannel.send(
                            RegisterSideEffect.NavigateToLoginScreen(
                                email,
                                password
                            )
                        )
                    }
                }
            }
        }
    }
}