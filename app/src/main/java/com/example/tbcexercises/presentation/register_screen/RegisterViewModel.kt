package com.example.tbcexercises.presentation.register_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.use_case.RegisterUseCase
import com.example.tbcexercises.domain.use_case.validation.ValidateEmailUseCase
import com.example.tbcexercises.domain.use_case.validation.ValidatePasswordUseCase
import com.example.tbcexercises.domain.use_case.validation.ValidateRepeatPasswordUseCase
import com.example.tbcexercises.utils.Resource
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
    private val registerUseCase: RegisterUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateRepeatPasswordUseCase: ValidateRepeatPasswordUseCase
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    private val _uiEventChannel = Channel<RegisterUiEvent>()
    val uiEvents = _uiEventChannel.receiveAsFlow()


    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.Register -> register(
                event.email,
                event.password,
                event.repeatedPassword
            )

            is RegisterEvent.EmailChanged -> validateEmail(event.email)
            is RegisterEvent.PasswordChanged -> validatePassword(event.password)
            is RegisterEvent.RepeatedPasswordChanged -> validateRepeatPassword(
                event.password,
                event.repeatedPassword
            )
        }
    }

    private fun register(email: String, password: String, repeatPassword: String) {
        if (!validateForm(email, password, repeatPassword)) return

        viewModelScope.launch {
            registerUseCase(email, password).collect { result ->
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
        val emailResult = validateEmailUseCase(email)
        val passwordResult = validatePasswordUseCase(password)
        val repeatPasswordResult = validateRepeatPasswordUseCase(password, repeatPassword)
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
        val result = validateEmailUseCase(email)
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
        val result = validatePasswordUseCase(password)
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
            validateRepeatPasswordUseCase(password = password, repeatedPassword = repeatPassword)
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