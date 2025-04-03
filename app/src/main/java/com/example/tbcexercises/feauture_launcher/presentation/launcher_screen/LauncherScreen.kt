package com.example.tbcexercises.feauture_launcher.presentation.launcher_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tbcexercises.R
import com.example.tbcexercises.core.presentation.resource.Colors
import kotlinx.coroutines.flow.combine

@Composable
fun LauncherScreen(
    viewModel: LaunchViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.WHITE)
    ) {
        val combinedState by viewModel.rememberMe
            .combine(viewModel.token) { rememberMe, token ->
                rememberMe to token
            }
            .collectAsStateWithLifecycle(null)

        if (combinedState == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Colors.WHITE),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.launching), color = Colors.BLACK)
            }
        } else {
            LaunchedEffect(combinedState) {
                val (rememberMe, token) = combinedState!!
                if (token.isNotEmpty()) {
                    if (rememberMe) {
                        navigateToHomeScreen()
                    } else {
                        navigateToLoginScreen()
                    }
                } else {
                    navigateToLoginScreen()
                }
            }
        }
    }
}