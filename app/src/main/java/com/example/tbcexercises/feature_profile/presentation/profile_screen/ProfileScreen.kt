package com.example.tbcexercises.feature_profile.presentation.profile_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tbcexercises.R
import com.example.tbcexercises.core.presentation.components.CustomButton
import com.example.tbcexercises.core.presentation.resource.Colors
import com.example.tbcexercises.core.presentation.resource.Dimens

@Composable
fun ProfileScreenRoot(
    viewModel: ProfileViewModel = hiltViewModel(),
    onLogOutClick: () -> Unit,
) {
    ProfileScreen(
        viewModel.emailFlow.collectAsStateWithLifecycle("").value,
        onLogOutClick
    ) { viewModel.clearUserSession() }
}


@Composable
fun ProfileScreen(
    email: String,
    onLogOutClick: () -> Unit = {},
    clearUserSession: () -> Unit,

    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.WHITE)
            .padding(Dimens.SCREEN_HORIZONTAL),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = email,
            color = Colors.BLACK,
            fontSize = Dimens.TEXT_SIZE_LARGE
        )
        Spacer(modifier = Modifier.height(Dimens.SPACING))

        CustomButton(text = stringResource(R.string.log_out), isEnabled = true) {
            clearUserSession()
            onLogOutClick()
        }
    }
}