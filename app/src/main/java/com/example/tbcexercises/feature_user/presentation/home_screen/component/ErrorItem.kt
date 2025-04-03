package com.example.tbcexercises.feature_user.presentation.home_screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.tbcexercises.R
import com.example.tbcexercises.core.presentation.resource.Colors
import com.example.tbcexercises.core.presentation.resource.Dimens

@Composable
fun ErrorItem(message: String, onClickRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.SCREEN_HORIZONTAL)
            .clickable { onClickRetry() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, color = Colors.RED)

        Spacer(modifier = Modifier.height(Dimens.SPACING_LOW))

        Button(onClick = onClickRetry) {
            Text(text = stringResource(R.string.retry))
        }

    }
}