package com.example.tbcexercises.feature_login.presentation.login_screen


import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.R
import com.example.tbcexercises.core.domain.util.ErrorTypes
import com.example.tbcexercises.core.presentation.base.BaseFragment
import com.example.tbcexercises.core.presentation.extension.collectLastState
import com.example.tbcexercises.core.presentation.extension.toMap
import com.example.tbcexercises.core.presentation.extension.toast
import com.example.tbcexercises.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()

    override fun start() {

        collectLastState(viewModel.uiState) { state ->
            updateUi(state)
        }
        collectLastState(viewModel.uiEvents) { event ->
            getEvents(event)
        }

    }

    private fun updateUi(state: LoginUiState) {
        showLoadingScreen(state.isLoading)

        binding.txtEmailError.apply {
            text = state.emailError?.let { getString(it.toMap()) }
            visibility =
                if (state.emailError == null || state.emailError == ErrorTypes.NON_VALIDATED)
                    View.GONE else View.VISIBLE
        }

        binding.txtPasswordError.apply {
            text = state.passwordError?.let { getString(it.toMap()) }
            visibility =
                if (state.passwordError == null || state.passwordError == ErrorTypes.NON_VALIDATED) View.GONE
                else View.VISIBLE
        }

        binding.btnLogin.apply {
            isEnabled = state.isValidForm
            if (state.isValidForm) {
                setBackgroundColor(context.getColor(R.color.dark_cyan))
            } else {
                setBackgroundColor(context.getColor(R.color.light_cyan))
            }
        }
    }

    private fun getEvents(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.SuccessFullLogin -> onSuccessFullLogin(
                binding.cbRememberMe.isChecked,
                binding.etEmail.text.toString()
            )

            is LoginUiEvent.ShowToast -> toast(event.message)
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

    override fun listeners() {
        registerListeners()

        binding.apply {
            etEmail.doOnTextChanged { text, _, _, _ ->
                viewModel.onEvent(LoginValidationEvent.ValidateEmail(text.toString()))
            }

            etPassword.doOnTextChanged { text, _, _, _ ->
                viewModel.onEvent(LoginValidationEvent.ValidatePassword(text.toString()))
            }

            btnLogin.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                viewModel.onEvent(
                    LoginValidationEvent.LoginValidation(
                        email = email,
                        password = password
                    )
                )
            }

            txtRegister.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
        }
    }

    private fun onSuccessFullLogin(rememberMe: Boolean, email: String) {
        viewModel.setSession(rememberMe, email)
        findNavController().navigate(LoginFragmentDirections.actionGlobalHomeFragment())
    }

    private fun showLoadingScreen(isLoading: Boolean) {
        val viewVisibility = if (!isLoading) View.VISIBLE else View.GONE

        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

            etEmail.visibility = viewVisibility
            btnLogin.visibility = viewVisibility
            txtRememberMe.visibility = viewVisibility
            cbRememberMe.visibility = viewVisibility
            textInputLayout.visibility = viewVisibility
            txtRegister.visibility = viewVisibility
            txtEmailError.visibility = viewVisibility
            txtPasswordError.visibility = viewVisibility
        }
    }
}