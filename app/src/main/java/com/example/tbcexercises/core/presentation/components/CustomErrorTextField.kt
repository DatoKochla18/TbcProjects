package com.example.tbcexercises.core.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomErrorTextField(text: String, modifier: Modifier = Modifier) {

    Text(
        text = text,
        color = Color.Red,
        fontSize = 12.sp,
        modifier = modifier
            .padding(start = 20.dp, end = 20.dp)
    )
}