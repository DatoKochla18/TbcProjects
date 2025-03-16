package com.example.tbcexercises.feature_register.presentation.register_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.core.domain.util.Result
import com.example.tbcexercises.feature_register.domain.use_case.RegisterUseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseWrapper: RegisterUseWrapper,
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    private val _uiEventChannel = Channel<RegisterSideEffect>()
    val uiEvents = _uiEventChannel.receiveAsFlow()


    fun onEvent(event: RegisterValidationEvent) {
        when (event) {
            is RegisterValidationEvent.Register -> register(
                event.email,
                event.password
            )

            is RegisterValidationEvent.ValidateEmail -> validateEmail(event.email)
            is RegisterValidationEvent.ValidatePassword -> validatePassword(event.password)
            is RegisterValidationEvent.ValidateRepeatedPassword -> validateRepeatPassword(
                event.password,
                event.repeatedPassword
            )
        }
    }

    private fun register(email: String, password: String) {
        viewModelScope.launch {
            registerUseWrapper.registerUseCase(email, password).collect { result ->
                when (result) {
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                            )
                        }
                        _uiEventChannel.send(RegisterSideEffect.ShowToast(result.error))
                    }

                    is Result.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _uiEventChannel.send(RegisterSideEffect.NavigateToLoginScreen)

                    }
                }
            }
        }
    }

    private fun validateEmail(email: String) {
        when (val result = registerUseWrapper.validateEmailUseCase(email)) {
            is Result.Error -> _uiState.update { it.copy(emailError = result.error) }
            is Result.Success -> _uiState.update {
                it.copy(
                    emailError = null, isEmailValid = true, isValidForm =
                    _uiState.value.isPasswordValid
                            && _uiState.value.isRepeatedPasswordValid
                )
            }
        }
    }

    private fun validatePassword(password: String) {
        when (val result = registerUseWrapper.validatePasswordUseCase(password)) {
            is Result.Error -> _uiState.update { it.copy(passwordError = result.error) }
            is Result.Success -> _uiState.update {
                it.copy(
                    passwordError = null, isPasswordValid = true, isValidForm =
                    _uiState.value.isEmailValid
                            && _uiState.value.isRepeatedPasswordValid
                )
            }
        }
    }

    private fun validateRepeatPassword(password: String, repeatPassword: String) {
        val result =
            registerUseWrapper.validateRepeatPasswordUseCase(
                password = password,
                repeatedPassword = repeatPassword
            )
        when (result) {
            is Result.Error -> _uiState.update { it.copy(repeatedPasswordError = result.error) }
            is Result.Success -> _uiState.update {
                it.copy(
                    repeatedPasswordError = null, isRepeatedPasswordValid = true, isValidForm =
                    _uiState.value.isEmailValid
                            && _uiState.value.isPasswordValid
                )
            }
        }
    }
}