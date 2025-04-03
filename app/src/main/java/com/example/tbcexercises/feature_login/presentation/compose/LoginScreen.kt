package com.example.tbcexercises.feature_login.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tbcexercises.R
import com.example.tbcexercises.core.domain.util.error.EmailError
import com.example.tbcexercises.core.domain.util.error.PasswordError
import com.example.tbcexercises.core.presentation.components.CustomButton
import com.example.tbcexercises.core.presentation.components.CustomErrorTextField
import com.example.tbcexercises.core.presentation.components.CustomPasswordField
import com.example.tbcexercises.core.presentation.extension.asString
import com.example.tbcexercises.core.presentation.extension.asStringResource
import com.example.tbcexercises.feature_login.presentation.login_screen.LoginEvent
import com.example.tbcexercises.feature_login.presentation.login_screen.LoginSideEffect
import com.example.tbcexercises.feature_login.presentation.login_screen.LoginUiState
import com.example.tbcexercises.feature_login.presentation.login_screen.LoginViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToRegisterScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    scaffoldState: SnackbarHostState,
) {


    LoginScreen(
        viewModel.uiState,
        onEvent = viewModel::onEvent,
        uiEvents = viewModel.uiEvents,
        navigateToRegisterScreen = navigateToRegisterScreen,
        navigateToHomeScreen = navigateToHomeScreen,
        scaffoldState = scaffoldState
    )
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onEvent: (LoginEvent) -> Unit,
    uiEvents: Flow<LoginSideEffect>,
    navigateToRegisterScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    scaffoldState: SnackbarHostState,
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        uiEvents.collect { event ->
            when (event) {
                is LoginSideEffect.ShowSnackBar -> {
                    scaffoldState.showSnackbar(
                        message = event.message.asString(context),
                        duration = SnackbarDuration.Short
                    )
                }

                LoginSideEffect.SuccessFullLogin -> {
                    navigateToHomeScreen()
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Text(
                text = stringResource(id = R.string.login),
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 20.dp, top = 10.dp)
            )

            OutlinedTextField(
                value = uiState.email,
                onValueChange = { onEvent(LoginEvent.OnEmailChanged(it)) },
                label = { Text(stringResource(id = R.string.email)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                singleLine = true,
                isError = uiState.emailError != null,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color.Black
                )
            )

            if (uiState.emailError != null) {
                CustomErrorTextField(
                    stringResource(uiState.emailError.asStringResource()),
                    Modifier
                        .align(Alignment.Start)
                        .padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            CustomPasswordField(
                currentValue = uiState.password,
                label = stringResource(R.string.password),
                isError = uiState.passwordError != null,
                toShowPassword = uiState.showPassword,
                onShowPasswordChanged = { onEvent(LoginEvent.SwitchShowPasswordStatus) }
            ) { onEvent(LoginEvent.OnPasswordChanged(it)) }

            if (uiState.passwordError != null) {
                CustomErrorTextField(
                    stringResource(uiState.passwordError.asStringResource()),
                    Modifier
                        .align(Alignment.Start)
                        .padding(start = 8.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.remember_me),
                    color = Color.Black
                )
                Checkbox(
                    checked = uiState.rememberMe,
                    onCheckedChange = { onEvent(LoginEvent.SwitchCheckBoxStatus) },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        uncheckedColor = Color.Black
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            CustomButton(text = stringResource(R.string.login), isEnabled = uiState.isValidForm) {
                onEvent(LoginEvent.Login(uiState.email, uiState.password))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.don_t_have_account_sign_up),
                color = Color.Black,
                modifier = Modifier.clickable {
                    navigateToRegisterScreen()
                }
            )

        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        uiState = LoginUiState(
            showPassword = true,
            isLoading = true,
            isValidForm = true,
            emailError = EmailError.INVALID_EMAIL,
            passwordError = PasswordError.SHORT_PASSWORD,
            password = "sakdl",
            rememberMe = true
        ),
        onEvent = {}, navigateToRegisterScreen = {},
        uiEvents = flow { }, navigateToHomeScreen = {}, scaffoldState = SnackbarHostState()
    )
}

