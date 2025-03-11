package com.example.tbcexercises.presentation.login_screen


import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentLoginBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.extension.collectLastState
import com.example.tbcexercises.presentation.extension.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()

    override fun start() {

        collectLastState(viewModel.uiState) { state ->
            showLoadingScreen(state.isLoading)


            binding.txtEmailError.apply {
                text = state.emailError
                visibility = if (!state.emailError.isNullOrEmpty()) View.VISIBLE else View.GONE
            }

            binding.txtPasswordError.apply {
                text = state.passwordError
                visibility = if (!state.passwordError.isNullOrEmpty()) View.VISIBLE else View.GONE
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
        collectLastState(viewModel.uiEvents) { event ->
            when (event) {
                is LoginUiEvent.SuccessFullLogin -> onSuccessFullLogin(
                    binding.cbRememberMe.isChecked,
                    binding.etEmail.text.toString()
                )

                is LoginUiEvent.ShowToast -> toast(event.message)
            }
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
                viewModel.onEvent(LoginEvent.ValidateEmail(text.toString()))
            }

            etPassword.doOnTextChanged { text, _, _, _ ->
                viewModel.onEvent(LoginEvent.ValidatePassword(text.toString()))
            }

            btnLogin.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                viewModel.onEvent(LoginEvent.Login(email = email, password = password))
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