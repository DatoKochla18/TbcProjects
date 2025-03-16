package com.example.tbcexercises.feature_login.presentation.login_screen


import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.R
import com.example.tbcexercises.core.presentation.base.BaseFragment
import com.example.tbcexercises.core.presentation.extension.asString
import com.example.tbcexercises.core.presentation.extension.asStringResource
import com.example.tbcexercises.core.presentation.extension.collectLastState
import com.example.tbcexercises.core.presentation.extension.toast
import com.example.tbcexercises.core.presentation.util.setViewsVisibility
import com.example.tbcexercises.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()

    override fun start() {
        listeners()
        collectLastState(viewModel.uiState) { state ->
            updateUi(state)
        }
        collectLastState(viewModel.uiEvents) { event ->
            getEvents(event)
        }

    }

    private fun updateUi(state: LoginUiState) {
        Log.d("state", state.toString())
        showLoadingScreen(state.isLoading)

        binding.txtEmailError.apply {
            text = state.emailError?.let { getString(it.asStringResource()) }
            isVisible = state.emailError != null
        }

        binding.txtPasswordError.apply {
            text = state.passwordError?.let { getString(it.asStringResource()) }
            isVisible = state.passwordError != null
        }

        binding.btnLogin.apply {
            isEnabled = state.isValidForm
            background = if (state.isValidForm) {
                ContextCompat.getDrawable(requireContext(), R.drawable.rounded_cyan_button)
            } else {
                ContextCompat.getDrawable(requireContext(), R.drawable.rounded_light_cyan_button)
            }
        }
    }

    private fun getEvents(event: LoginSideEffect) {
        when (event) {
            is LoginSideEffect.SuccessFullLogin -> onSuccessFullLogin(binding.cbRememberMe.isChecked)

            is LoginSideEffect.ShowToast -> toast(event.message.asString(requireContext()))
        }
    }

    private fun registerListeners() {
        parentFragmentManager.setFragmentResultListener("authData", this) { _, bundle ->
            val email = bundle.getString("email")
            val password = bundle.getString("password")

            binding.apply {
                etEmail.setText(email)
                etPassword.setText(password)
            }
        }
    }

    private fun listeners() {
        registerListeners()

        binding.apply {
            etEmail.doAfterTextChanged { text ->
                viewModel.onEvent(LoginEvent.ValidateEmail(text.toString()))
            }

            etPassword.doAfterTextChanged { text ->
                viewModel.onEvent(LoginEvent.ValidatePassword(text.toString()))
            }

            btnLogin.setOnClickListener {
                login()
            }

            txtRegister.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
        }
    }

    private fun login() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        viewModel.onEvent(
            LoginEvent.Login(
                email = email,
                password = password
            )
        )
    }


    private fun onSuccessFullLogin(rememberMe: Boolean) {
        viewModel.saveRememberMe(rememberMe)
        findNavController().navigate(LoginFragmentDirections.actionGlobalHomeFragment())
    }

    private fun showLoadingScreen(isLoading: Boolean) {
        binding.apply {
            setViewsVisibility(
                isLoading,
                progressBar,
                etEmail,
                btnLogin,
                txtRememberMe,
                cbRememberMe,
                textInputLayout,
                txtRegister,
                txtEmailError,
                txtPasswordError
            )
        }
    }
}