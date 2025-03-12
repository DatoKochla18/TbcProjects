package com.example.tbcexercises.feature_register.presentation.register_screen


import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.R
import com.example.tbcexercises.core.domain.util.ErrorTypes
import com.example.tbcexercises.core.presentation.base.BaseFragment
import com.example.tbcexercises.core.presentation.extension.collectLastState
import com.example.tbcexercises.core.presentation.extension.toMap
import com.example.tbcexercises.core.presentation.extension.toast
import com.example.tbcexercises.core.presentation.util.setViewsVisibility
import com.example.tbcexercises.databinding.FragmentRegisterBinding
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
            text = state.emailError?.let { getString(it.toMap()) }
            visibility =
                if (state.emailError == null || state.emailError == ErrorTypes.NON_VALIDATED)
                    View.GONE else View.VISIBLE
        }

        binding.txtPasswordError.apply {
            text = state.passwordError?.let { getString(it.toMap()) }
            visibility =
                if (state.passwordError == null || state.passwordError == ErrorTypes.NON_VALIDATED)
                    View.GONE else View.VISIBLE
        }
        binding.txtPasswordRepeatError.apply {
            text = state.repeatedPasswordError?.let { getString(it.toMap()) }
            visibility =
                if (state.repeatedPasswordError == null ||
                    state.repeatedPasswordError == ErrorTypes.NON_VALIDATED
                )
                    View.GONE else View.VISIBLE
        }

        binding.btnRegister.apply {
            isEnabled = state.isValidForm
            background = if (state.isValidForm) {
                ContextCompat.getDrawable(requireContext(), R.drawable.rounded_cyan_button)
            } else {
                ContextCompat.getDrawable(requireContext(), R.drawable.rounded_light_cyan_button)
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

        binding.apply {
            etEmail.doAfterTextChanged {
                viewModel.onEvent(RegisterValidationEvent.ValidateEmail(it.toString()))
            }
            etPassword.doAfterTextChanged {
                viewModel.onEvent(RegisterValidationEvent.ValidatePassword(it.toString()))
            }
            etPasswordRepeat.doAfterTextChanged {
                viewModel.onEvent(

                    RegisterValidationEvent.ValidateRepeatedPassword(
                        binding.etPassword.text.toString(),
                        it.toString()
                    )
                )
            }
        }
    }

    private fun showLoadingScreen(isLoading: Boolean) {
        binding.apply {
            setViewsVisibility(
                isLoading,
                progressBar,
                etEmail,
                btnRegister,
                textInputLayout,
                textInputLayoutRepeat,
                txtPasswordError,
                txtEmailError,
                txtPasswordRepeatError
            )
        }
    }
}