package com.example.tbcexercises.feature_register.presentation.register_screen


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.core.presentation.extension.asString
import com.example.tbcexercises.feature_register.presentation.compose.RegisterScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val viewModel: RegisterViewModel by viewModels()

    private lateinit var composeView: ComposeView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
        }.also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        composeView.setContent {
            val snackBarHostState = remember { SnackbarHostState() }
            val uiState = viewModel.uiState

            SideEffectListener(snackBarHostState)

            UiSetUp(snackBarHostState, uiState)
        }
    }

    @Composable
    private fun UiSetUp(snackBarHostState: SnackbarHostState, uiState: RegisterUiState) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
        ) { pad ->
            pad
            RegisterScreen(
                uiState = uiState,
                onEmailChanged = { viewModel.onEvent(RegisterEvent.OnEmailChanged(it)) },
                onPasswordChanged = { viewModel.onEvent(RegisterEvent.OnPasswordChanged(it)) },
                onRepeatPasswordChanged = {
                    viewModel.onEvent(
                        RegisterEvent.OnRepeatedPasswordChanged(
                            it
                        )
                    )
                },
                register = { email, password ->
                    viewModel.onEvent(
                        RegisterEvent.Register(
                            email,
                            password
                        )
                    )
                },
                onShowPasswordChanged = { viewModel.onEvent(RegisterEvent.OnShowPasswordChanged) },
            )
        }
    }

    @Composable
    private fun SideEffectListener(snackBarHostState: SnackbarHostState) {
        LaunchedEffect(key1 = true) {
            viewModel.uiEvents.collect { event ->
                when (event) {
                    is RegisterSideEffect.NavigateToLoginScreen -> {
                        val authData =
                            bundleOf("email" to event.email, "password" to event.password)
                        setFragmentResult("authData", authData)
                        findNavController().popBackStack()
                    }

                    is RegisterSideEffect.ShowError -> {
                        snackBarHostState.showSnackbar(
                            message = event.message.asString(requireContext())
                        )
                    }
                }
            }
        }
    }
}