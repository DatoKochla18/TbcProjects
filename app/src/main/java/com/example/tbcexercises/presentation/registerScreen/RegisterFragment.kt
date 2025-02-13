package com.example.tbcexercises.presentation.registerScreen


import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.App
import com.example.tbcexercises.R
import com.example.tbcexercises.utils.common.Resource
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.databinding.FragmentRegisterBinding
import com.example.tbcexercises.utils.exntension.collectLastState
import com.example.tbcexercises.utils.exntension.isEmailValid
import com.example.tbcexercises.utils.exntension.toast
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: RegisterViewModel by viewModels()
    override fun start() {
        collectLastState(viewModel.registerResponse) { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoadingScreen(true)
                }

                is Resource.Error -> {
                    showLoadingScreen(false)
                    toast(result.message)
                }

                is Resource.Success -> {
                    val email = binding.etEmail.text.toString()
                    val password = binding.etPassword.text.toString()
                    showLoadingScreen(false)
                    val authData = bundleOf("email" to email, "password" to password)
                    setFragmentResult("authData", authData)
                    findNavController().popBackStack()
                }

                null -> {
                }
            }
        }
    }

    override fun listeners() {
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val passwordRepeat = binding.etPasswordRepeat.text.toString()

            viewModel.register(email = email, password = password, passwordRepeat = passwordRepeat)
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