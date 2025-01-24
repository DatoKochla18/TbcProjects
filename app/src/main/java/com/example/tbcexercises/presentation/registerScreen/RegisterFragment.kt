package com.example.tbcexercises.presentation.registerScreen


import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.R
import com.example.tbcexercises.base.BaseFragment
import com.example.tbcexercises.databinding.FragmentRegisterBinding
import com.example.tbcexercises.utils.Result
import com.example.tbcexercises.utils.exntension.collectLastState
import com.example.tbcexercises.utils.exntension.isEmailValid
import com.example.tbcexercises.utils.exntension.toast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: RegisterViewModel by viewModels()

    override fun start() {}

    override fun listeners() {
        binding.btnRegister.setOnClickListener {
            registerController()
        }
    }

    private fun registerController() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val passwordRepeat = binding.etPasswordRepeat.text.toString()

        if (email.isEmailValid() && password.isNotEmpty() && passwordRepeat == password && password.length >= 6) {
            register(email, password)
        } else {
            when {
                !email.isEmailValid() -> toast(getString(R.string.enter_valid_email))
                password.isEmpty() -> toast(getString(R.string.password_should_not_be_empty))
                password.length < 6 -> toast(getString(R.string.password_should_at_least_6_character))
                else -> toast("two password should be same")
                //passwordRepeat!=password this will be always false
            }
        }
    }

    private fun register(email: String, password: String) {

        viewModel.register(email, password)

        collectLastState(viewModel.registerResponse) { result ->
            when (result) {
                is Result.Error -> {
                    showLoadingScreen(false)
                    toast(result.message)
                }

                Result.Loading -> showLoadingScreen(true)
                is Result.Success -> {
                    val authData = bundleOf("email" to email, "password" to password)
                    setFragmentResult("authData", authData)
                    findNavController().popBackStack()
                }

                null -> {}
            }

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
        }
    }
}