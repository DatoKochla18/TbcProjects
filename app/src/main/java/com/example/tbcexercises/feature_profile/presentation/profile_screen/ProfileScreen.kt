package com.example.tbcexercises.feature_profile.presentation.profile_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tbcexercises.R
import com.example.tbcexercises.core.presentation.components.CustomButton
import com.example.tbcexercises.core.presentation.extension.CollectAsUiEvents
import com.example.tbcexercises.core.presentation.resource.Colors
import com.example.tbcexercises.core.presentation.resource.Dimens
import kotlinx.coroutines.flow.Flow

@Composable
fun ProfileScreenRoot(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit,
) {
    val uiState = viewModel.uiState



    ProfileScreen(
        uiState = uiState,
        uiSideEffect = viewModel.sideEffect,
        onEvent = viewModel::onEvent,
        navigateToLoginScreen = navigateToLoginScreen,
    )
}

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    uiSideEffect: Flow<ProfileSideEffect>,
    navigateToLoginScreen: () -> Unit,
    onEvent: (ProfileEvent) -> Unit,
) {


    uiSideEffect.CollectAsUiEvents { event ->
        when (event) {
            ProfileSideEffect.OnSuccessfulLogout -> navigateToLoginScreen()
        }
    }

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.WHITE)
                .padding(Dimens.SCREEN_HORIZONTAL),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = uiState.email,
                color = Colors.BLACK,
                fontSize = Dimens.TEXT_SIZE_LARGE
            )
            Spacer(modifier = Modifier.height(Dimens.SPACING))

            CustomButton(text = stringResource(R.string.log_out), isEnabled = true) {
                onEvent(ProfileEvent.OnLogOutClicked)
            }
        }
    }
}