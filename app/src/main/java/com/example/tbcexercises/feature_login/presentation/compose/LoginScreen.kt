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
import com.example.tbcexercises.core.domain.util.error.EmailError
import com.example.tbcexercises.core.domain.util.error.PasswordError
import com.example.tbcexercises.core.presentation.components.CustomButton
import com.example.tbcexercises.core.presentation.components.CustomErrorTextField
import com.example.tbcexercises.core.presentation.components.CustomPasswordField
import com.example.tbcexercises.core.presentation.extension.asStringResource
import com.example.tbcexercises.feature_login.presentation.login_screen.LoginUiState

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    updateRememberMe: (Boolean) -> Unit,
    login: (String, String) -> Unit,
    navigateToRegisterScreen: () -> Unit,
    onShowPasswordChanged: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.login),
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        // Email Field
        OutlinedTextField(
            value = uiState.email,
            onValueChange = { onEmailChanged(it) },
            label = { Text(stringResource(id = R.string.email)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = uiState.emailError != null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.Black
            )
        )

        // Email Error
        if (uiState.emailError != null) {
            CustomErrorTextField(
                stringResource(uiState.emailError.asStringResource()),
                Modifier
                    .align(Alignment.Start)
                    .padding(start = 8.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Password Field
        CustomPasswordField(
            currentValue = uiState.password,
            label = stringResource(R.string.password),
            isError = uiState.passwordError != null,
            toShowPassword = uiState.showPassword,
            onShowPasswordChanged = onShowPasswordChanged
        ) { onPasswordChanged(it) }

        // Password Error
        if (uiState.passwordError != null) {
            CustomErrorTextField(
                stringResource(uiState.passwordError.asStringResource()),
                Modifier
                    .align(Alignment.Start)
                    .padding(start = 8.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Remember Me Checkbox
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.remember_me),
                color = Color.Black
            )
            Checkbox(
                checked = uiState.rememberMe,
                onCheckedChange = { updateRememberMe(it) },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = Color.Black
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Login Button
        CustomButton(text = stringResource(R.string.login), isEnabled = uiState.isValidForm) {
            login(uiState.email, uiState.password)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Register Text
        Text(
            text = stringResource(id = R.string.don_t_have_account_sign_up),
            color = Color.Black,
            modifier = Modifier.clickable {
                navigateToRegisterScreen()
            }
        )

        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        uiState = LoginUiState(
            showPassword = true,
            isValidForm = true,
            emailError = EmailError.INVALID_EMAIL,
            passwordError = PasswordError.SHORT_PASSWORD,
            password = "sakdl",
            rememberMe = true
        ),
        onEmailChanged = {},
        onPasswordChanged = {},
        navigateToRegisterScreen = {},
        updateRememberMe = {},
        login = { a, b -> },

        ) { }
}

