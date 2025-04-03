package com.example.tbcexercises.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.tbcexercises.core.presentation.resource.Colors
import com.example.tbcexercises.core.presentation.resource.Dimens


@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = false,
    onClick: () -> Unit,
) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.BUTTON_HEIGHT)
            .padding(horizontal = Dimens.SCREEN_HORIZONTAL),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isEnabled) Colors.CYAN else Colors.CYAN.copy(alpha = 0.5f),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(Dimens.ROUNDED_CORNER_LOW)
    ) {
        Text(text = text)
    }

}

