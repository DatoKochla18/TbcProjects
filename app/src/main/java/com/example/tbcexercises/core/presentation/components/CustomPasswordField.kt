package com.example.tbcexercises.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.tbcexercises.core.presentation.resource.Colors
import com.example.tbcexercises.core.presentation.resource.Dimens

@Composable
fun CustomPasswordField(
    currentValue: String,
    label: String,
    isError: Boolean = false,
    toShowPassword: Boolean = false,
    onShowPasswordChanged: () -> Unit,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = currentValue,
        onValueChange = { onValueChange(it) },
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.SCREEN_HORIZONTAL),
        singleLine = true,
        visualTransformation = if (toShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon =
                if (toShowPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff
            IconButton(onClick = { onShowPasswordChanged() }) {
                Icon(imageVector = icon, contentDescription = "Toggle password visibility")
            }
        },
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Colors.BLACK,
            unfocusedBorderColor = Colors.GRAY,
            focusedTextColor = Colors.BLACK
        )
    )
}