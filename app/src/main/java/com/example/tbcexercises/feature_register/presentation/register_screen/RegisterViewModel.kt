package com.example.tbcexercises.feature_register.presentation.register_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.core.utils.Resource
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

    private val _uiEventChannel = Channel<RegisterUiEvent>()
    val uiEvents = _uiEventChannel.receiveAsFlow()


    fun onEvent(event: RegisterValidationEvent) {
        when (event) {
            is RegisterValidationEvent.Register -> register(
                event.email,
                event.password,
                event.repeatedPassword
            )

            is RegisterValidationEvent.ValidateEmail -> validateEmail(event.email)
            is RegisterValidationEvent.ValidatePassword -> validatePassword(event.password)
            is RegisterValidationEvent.ValidateRepeatedPassword -> validateRepeatPassword(
                event.password,
                event.repeatedPassword
            )
        }
    }

    private fun register(email: String, password: String, repeatPassword: String) {
        if (!validateForm(email, password, repeatPassword)) return

        viewModelScope.launch {
            registerUseWrapper.registerUseCase(email, password).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _uiEventChannel.send(RegisterUiEvent.NavigateToLoginScreen)

                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                            )
                        }
                        _uiEventChannel.send(RegisterUiEvent.ShowToast(result.message))
                    }
                }
            }
        }
    }

    private fun validateForm(email: String, password: String, repeatPassword: String): Boolean {
        val emailResult = registerUseWrapper.validateEmailUseCase(email)
        val passwordResult = registerUseWrapper.validatePasswordUseCase(password)
        val repeatPasswordResult =
            registerUseWrapper.validateRepeatPasswordUseCase(password, repeatPassword)
        val hasError =
            !emailResult.successful || !passwordResult.successful || !repeatPasswordResult.successful

        _uiState.update {
            it.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                isValidForm = !hasError
            )
        }

        return !hasError
    }

    private fun validateEmail(email: String) {
        val result = registerUseWrapper.validateEmailUseCase(email)
        _uiState.update {
            it.copy(
                emailError = result.errorMessage,
                isValidForm = result.successful && (
                        _uiState.value.passwordError == null
                                && _uiState.value.repeatedPasswordError == null)
            )
        }
    }

    private fun validatePassword(password: String) {
        val result = registerUseWrapper.validatePasswordUseCase(password)
        _uiState.update {
            it.copy(
                passwordError = result.errorMessage,
                isValidForm = result.successful && (
                        _uiState.value.emailError == null
                                && _uiState.value.repeatedPasswordError == null)
            )
        }
    }

    private fun validateRepeatPassword(password: String, repeatPassword: String) {
        val result =
            registerUseWrapper.validateRepeatPasswordUseCase(
                password = password,
                repeatedPassword = repeatPassword
            )
        _uiState.update {
            it.copy(
                repeatedPasswordError = result.errorMessage,
                isValidForm = result.successful && (
                        _uiState.value.emailError == null
                                && _uiState.value.passwordError == null)
            )
        }
    }
}