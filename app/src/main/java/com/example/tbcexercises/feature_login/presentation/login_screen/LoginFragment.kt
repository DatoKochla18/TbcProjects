package com.example.tbcexercises.feature_login.presentation.login_screen


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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.core.presentation.extension.asString
import com.example.tbcexercises.feature_login.presentation.compose.LoginScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels()
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

        registerListeners()

        composeView.setContent {
            val snackBarHostState = remember { SnackbarHostState() }
            val uiState = viewModel.uiState

            SideEffectsListener(snackBarHostState, uiState)

            UiSetUp(snackBarHostState, uiState)

        }
    }

    @Composable
    private fun UiSetUp(snackBarHostState: SnackbarHostState, uiState: LoginUiState) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
        ) { pad ->
            pad
            LoginScreen(
                uiState = uiState,
                onEmailChanged = { viewModel.onEvent(LoginEvent.OnEmailChanged(it)) },
                onPasswordChanged = { viewModel.onEvent(LoginEvent.OnPasswordChanged(it)) },
                updateRememberMe = { viewModel.onEvent(LoginEvent.SwitchCheckBoxStatus) },
                login = { email, password ->
                    viewModel.onEvent(
                        LoginEvent.Login(
                            email,
                            password
                        )
                    )
                },
                navigateToRegisterScreen = {
                    findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                    )
                },
                onShowPasswordChanged = { viewModel.onEvent(LoginEvent.SwitchShowPasswordStatus) },
            )
        }
    }

    @Composable
    private fun SideEffectsListener(snackBarHostState: SnackbarHostState, uiState: LoginUiState) {
        LaunchedEffect(key1 = true) {
            viewModel.uiEvents.collect { event ->
                when (event) {
                    is LoginSideEffect.SuccessFullLogin -> {
                        viewModel.saveRememberMe(uiState.rememberMe)
                        findNavController().navigate(LoginFragmentDirections.actionGlobalHomeFragment())
                    }

                    is LoginSideEffect.ShowSnackBar -> {
                        snackBarHostState.showSnackbar(
                            message = event.message.asString(requireContext())
                        )
                    }
                }
            }
        }
    }

    private fun registerListeners() {
        parentFragmentManager.setFragmentResultListener("authData", this) { _, bundle ->
            val email = bundle.getString("email")
            val password = bundle.getString("password")
            if (email != null && password != null) {
                viewModel.onEvent(
                    LoginEvent.GetResultFromRegister(
                        email,
                        password
                    )
                )
            }
        }
    }

}