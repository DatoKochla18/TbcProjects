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
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
            .background(Color.White)
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
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Launching...", color = Color.Black)
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