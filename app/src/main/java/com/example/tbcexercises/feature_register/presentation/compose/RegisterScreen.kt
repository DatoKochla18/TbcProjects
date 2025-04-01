package com.example.tbcexercises.feature_register.presentation.compose

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tbcexercises.R
import com.example.tbcexercises.core.presentation.components.CustomButton
import com.example.tbcexercises.core.presentation.components.CustomErrorTextField
import com.example.tbcexercises.core.presentation.components.CustomPasswordField
import com.example.tbcexercises.core.presentation.extension.asStringResource
import com.example.tbcexercises.feature_register.presentation.register_screen.RegisterUiState

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRepeatPasswordChanged: (String) -> Unit,
    register: (String, String) -> Unit,
    onShowPasswordChanged: () -> Unit,
) {


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
                text = stringResource(id = R.string.register),
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 20.dp, top = 10.dp)
            )

            OutlinedTextField(
                value = uiState.email,
                onValueChange = { onEmailChanged(it) },
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
                        .padding(start = 8.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            CustomPasswordField(
                currentValue = uiState.password,
                label = stringResource(R.string.password),
                isError = uiState.passwordError != null,
                toShowPassword = uiState.showPassword,
                onShowPasswordChanged = onShowPasswordChanged
            ) { onPasswordChanged(it) }

            if (uiState.passwordError != null) {
                CustomErrorTextField(
                    stringResource(uiState.passwordError.asStringResource()),
                    Modifier
                        .align(Alignment.Start)
                        .padding(start = 8.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            CustomPasswordField(
                currentValue = uiState.repeatPassword,
                label = stringResource(R.string.repeat_password),
                isError = uiState.repeatedPasswordError != null,
                toShowPassword = uiState.showPassword,
                onShowPasswordChanged = onShowPasswordChanged
            ) { onRepeatPasswordChanged(it) }

            if (uiState.repeatedPasswordError != null) {
                CustomErrorTextField(
                    stringResource(uiState.repeatedPasswordError.asStringResource()),
                    Modifier
                        .align(Alignment.Start)
                        .padding(start = 8.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))




            CustomButton(
                text = stringResource(R.string.register),
                isEnabled = uiState.isValidForm
            ) {
                register(uiState.email, uiState.password)
            }
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(uiState = RegisterUiState(isLoading = true),
        onShowPasswordChanged = {},
        onEmailChanged = {},
        onPasswordChanged = {},
        onRepeatPasswordChanged = {},
        register = { a, b -> }
    )
}