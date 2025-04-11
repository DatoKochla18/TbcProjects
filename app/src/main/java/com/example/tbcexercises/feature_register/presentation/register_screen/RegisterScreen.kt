package com.example.tbcexercises.feature_register.presentation.register_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tbcexercises.R
import com.example.tbcexercises.core.presentation.components.CustomButton
import com.example.tbcexercises.core.presentation.components.CustomErrorTextField
import com.example.tbcexercises.core.presentation.components.CustomPasswordField
import com.example.tbcexercises.core.presentation.extension.CollectAsUiEvents
import com.example.tbcexercises.core.presentation.extension.asStringResource
import com.example.tbcexercises.core.presentation.resource.Colors
import com.example.tbcexercises.core.presentation.resource.Dimens
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun RegisterRootScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState,
    navigateToLoginScreen: (String, String) -> Unit,

    ) {
    RegisterScreen(
        viewModel.uiState,
        onEvent = viewModel::onEvent,
        uiEvents = viewModel.uiEvents,
        snackBarHostState = snackBarHostState,
        navigateToLoginScreen = { email, password ->
            navigateToLoginScreen(
                email, password
            )
        }
    )
}

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    uiEvents: Flow<RegisterSideEffect>,
    onEvent: (RegisterEvent) -> Unit,
    navigateToLoginScreen: (String, String) -> Unit,
    snackBarHostState: SnackbarHostState,

    ) {
    val context = LocalContext.current

    uiEvents.CollectAsUiEvents { event ->
        when (event) {
            is RegisterSideEffect.NavigateToLoginScreen -> navigateToLoginScreen(
                event.email,
                event.password
            )

            is RegisterSideEffect.ShowError -> snackBarHostState.showSnackbar(
                message = context.getString(event.message),
                duration = SnackbarDuration.Short
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.WHITE),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Colors.WHITE),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {

            OutlinedTextField(
                value = uiState.email,
                onValueChange = { onEvent(RegisterEvent.OnEmailChanged(it)) },
                label = { Text(stringResource(id = R.string.email)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.SCREEN_HORIZONTAL),
                singleLine = true,
                isError = uiState.emailError != null,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Colors.BLACK,
                    unfocusedBorderColor = Colors.GRAY,
                    focusedTextColor = Colors.BLACK
                )
            )

            if (uiState.emailError != null) {
                CustomErrorTextField(
                    stringResource(uiState.emailError.asStringResource()),
                    Modifier
                        .align(Alignment.Start)
                        .padding(top = Dimens.ERROR_FIELD_TOP)
                )
            }

            Spacer(modifier = Modifier.height(Dimens.SPACING))

            CustomPasswordField(currentValue = uiState.password,
                label = stringResource(R.string.password),
                isError = uiState.passwordError != null,
                toShowPassword = uiState.showPassword,
                onShowPasswordChanged = { onEvent(RegisterEvent.OnShowPasswordChanged) }) {
                onEvent(
                    RegisterEvent.OnPasswordChanged(it)
                )
            }

            if (uiState.passwordError != null) {
                CustomErrorTextField(
                    stringResource(uiState.passwordError.asStringResource()),
                    Modifier
                        .align(Alignment.Start)
                        .padding(top = Dimens.ERROR_FIELD_TOP)
                )
            }

            Spacer(modifier = Modifier.height(Dimens.SPACING))

            CustomPasswordField(currentValue = uiState.repeatPassword,
                label = stringResource(R.string.repeat_password),
                isError = uiState.repeatedPasswordError != null,
                toShowPassword = uiState.showPassword,
                onShowPasswordChanged = { onEvent(RegisterEvent.OnShowPasswordChanged) }) {
                onEvent(
                    RegisterEvent.OnRepeatedPasswordChanged(it)
                )
            }

            if (uiState.repeatedPasswordError != null) {
                CustomErrorTextField(
                    stringResource(uiState.repeatedPasswordError.asStringResource()),
                    Modifier
                        .align(Alignment.Start)
                        .padding(top = Dimens.ERROR_FIELD_TOP)
                )
            }

            Spacer(modifier = Modifier.height(Dimens.SPACING))


            CustomButton(
                text = stringResource(R.string.register), isEnabled = uiState.isValidForm
            ) {
                onEvent(RegisterEvent.Register(uiState.email, uiState.password))
            }
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(uiState = RegisterUiState(isLoading = false),
        onEvent = {},
        uiEvents = flow { },
        snackBarHostState = SnackbarHostState(),
        navigateToLoginScreen = { a, b -> })
}