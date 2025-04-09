@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.example.tbcexercises.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.tbcexercises.R
import com.example.tbcexercises.core.presentation.resource.Colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(title: String, showBackButton: Boolean, onBackClicked: () -> Unit) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = title,
            color = Colors.BLACK,
            maxLines = 1,
        )
    }, navigationIcon = {
        if (showBackButton) {
            IconButton(onClick = { onBackClicked() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = Color.Black
                )
            }
        }
    }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = Color.Gray
    )
    )
}


@Preview
@Composable
fun AppTopBarPreview() {
    AppTopBar("", true, {})
}