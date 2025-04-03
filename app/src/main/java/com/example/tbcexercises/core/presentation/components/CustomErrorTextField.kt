package com.example.tbcexercises.core.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tbcexercises.core.presentation.resource.Colors
import com.example.tbcexercises.core.presentation.resource.Dimens

@Composable
fun CustomErrorTextField(text: String, modifier: Modifier = Modifier) {

    Text(
        text = text,
        color = Colors.RED,
        fontSize = Dimens.TEXT_SIZE_SMALL,
        modifier = modifier
            .padding(horizontal = Dimens.SCREEN_HORIZONTAL)
    )
}