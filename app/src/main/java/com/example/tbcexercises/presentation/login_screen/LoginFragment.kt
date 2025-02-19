package com.example.tbcexercises.presentation.login_screen


import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.utils.common.Resource
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.databinding.FragmentLoginBinding
import com.example.tbcexercises.utils.exntension.collectLastState
import com.example.tbcexercises.utils.exntension.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()


    override fun start() {
        collectLastState(viewModel.loginResponse) { result ->
            when (result) {
                is Resource.Loading -> showLoadingScreen(true)
                is Resource.Error -> {
                    showLoadingScreen(false)
                    toast(result.message)
                }

                is Resource.Success -> {
                    viewModel.setSession(
                        binding.cbRememberMe.isChecked,
                        binding.etEmail.text.toString()
                    )
                    navigateToHomeScreen()
                }

                null -> {}
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
            btnLogin.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                viewModel.login(email = email, password = password)
            }
            txtRegister.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
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