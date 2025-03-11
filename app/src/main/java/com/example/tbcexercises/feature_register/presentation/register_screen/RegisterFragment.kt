package com.example.tbcexercises.feature_register.presentation.register_screen


import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentRegisterBinding
import com.example.tbcexercises.core.presentation.base.BaseFragment
import com.example.tbcexercises.core.presentation.extension.collectLastState
import com.example.tbcexercises.core.presentation.extension.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: RegisterViewModel by viewModels()
    override fun start() {
        setupTextWatchers()
        collectLastState(viewModel.uiState) { state ->
            updateUiState(state)
        }

        collectLastState(viewModel.uiEvents) { event ->
            getEvents(event)
        }
    }

    override fun listeners() {
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val passwordRepeat = binding.etPasswordRepeat.text.toString()

            viewModel.onEvent(
                RegisterValidationEvent.Register(
                    email = email,
                    password = password,
                    repeatedPassword = passwordRepeat
                )
            )
        }
    }

    private fun updateUiState(state: RegisterUiState) {
        showLoadingScreen(state.isLoading)


        binding.txtEmailError.apply {
            text = state.emailError
            visibility = if (!state.emailError.isNullOrEmpty()) View.VISIBLE else View.GONE
        }

        binding.txtPasswordError.apply {
            text = state.passwordError
            visibility = if (!state.passwordError.isNullOrEmpty()) View.VISIBLE else View.GONE
        }
        binding.txtPasswordRepeatError.apply {
            text = state.repeatedPasswordError
            visibility =
                if (!state.repeatedPasswordError.isNullOrEmpty()) View.VISIBLE else View.GONE
        }

        binding.btnRegister.apply {
            isEnabled = state.isValidForm
            if (state.isValidForm) {
                setBackgroundColor(context.getColor(R.color.dark_cyan))
            } else {
                setBackgroundColor(context.getColor(R.color.light_cyan))
            }
        }
    }

    private fun getEvents(event: RegisterUiEvent) {
        when (event) {
            RegisterUiEvent.NavigateToLoginScreen -> {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                showLoadingScreen(false)
                val authData = bundleOf("email" to email, "password" to password)
                setFragmentResult("authData", authData)
                findNavController().popBackStack()
            }

            is RegisterUiEvent.ShowToast -> {
                toast(event.message)
            }
        }
    }

    private fun setupTextWatchers() {
        binding.etEmail.doAfterTextChanged {
            viewModel.onEvent(RegisterValidationEvent.EmailChanged(it.toString()))
        }

        binding.etPassword.doAfterTextChanged {
            viewModel.onEvent(RegisterValidationEvent.PasswordChanged(it.toString()))
        }

        binding.etPasswordRepeat.doAfterTextChanged {
            viewModel.onEvent(

                RegisterValidationEvent.RepeatedPasswordChanged(
                    binding.etPassword.text.toString(),
                    it.toString()
                )
            )
        }
    }

    private fun showLoadingScreen(isLoading: Boolean) {
        val viewVisibility = if (!isLoading) View.VISIBLE else View.GONE
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            etEmail.visibility = viewVisibility
            btnRegister.visibility = viewVisibility
            textInputLayout.visibility = viewVisibility
            textInputLayoutRepeat.visibility = viewVisibility
            txtPasswordError.visibility = viewVisibility
            txtEmailError.visibility = viewVisibility
            txtPasswordRepeatError.visibility = viewVisibility

        }
    }
}