package com.example.tbcexercises.presentation.loginScreen


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.R
import com.example.tbcexercises.base.BaseFragment
import com.example.tbcexercises.databinding.FragmentLoginBinding
import com.example.tbcexercises.utils.exntension.collectLastState
import com.example.tbcexercises.utils.exntension.dataStore
import com.example.tbcexercises.utils.exntension.isEmailValid
import com.example.tbcexercises.utils.exntension.toast


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        parentFragmentManager.setFragmentResultListener("authData", this) { _, bundle ->
            val email = bundle.getString("email")
            val password = bundle.getString("password")

            binding.apply {
                etEmail.setText(email)
                etPassword.setText(password)
            }
        }
        super.onCreate(savedInstanceState)
    }

    override fun start() {
    }

    override fun listeners() {
        binding.apply {
            btnLogin.setOnClickListener {
                loginController()
            }
            txtRegister.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
        }


    }

    private fun loginController() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        if (email.isEmailValid() && password.isNotEmpty() && password.length >= 6) {
            login(email, password)
        } else {
            when {
                !email.isEmailValid() -> toast(getString(R.string.enter_valid_email))
                password.isEmpty() -> toast(getString(R.string.password_should_not_be_empty))
                password.length < 6 -> toast(getString(R.string.password_should_at_least_6_character))
                else -> toast(getString(R.string.unexpected_error_backend_problem))
            }
        }
    }

    private fun login(email: String, password: String) {

        viewModel.login(email, password)

        collectLastState(viewModel.loginResponse) { result ->
            showLoadingScreen(result.loading)

            result.success?.let {
                setSession(binding.cbRememberMe.isChecked, email)
                navigateToHomeScreen()
            }

            result.error?.let {
                toast(getString(it))
            }
        }
    }


    private suspend fun setSession(rememberMe: Boolean, email: String) {
        requireContext().dataStore.updateData {
            it.copy(rememberMe = rememberMe, email = email)
        }
    }

    private fun navigateToHomeScreen() {
        findNavController().navigate(LoginFragmentDirections.actionGlobalHomeFragment())
    }

    private fun showLoadingScreen(isLoading: Boolean) {
        val viewVisibility = if (!isLoading) View.VISIBLE else View.GONE

        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

            etEmail.visibility = viewVisibility
            btnLogin.visibility = viewVisibility
            btnLogin.visibility = viewVisibility
            btnLogin.visibility = viewVisibility
            txtRememberMe.visibility = viewVisibility
            cbRememberMe.visibility = viewVisibility
            textInputLayout.visibility = viewVisibility
            txtRegister.visibility = viewVisibility
        }
    }

}