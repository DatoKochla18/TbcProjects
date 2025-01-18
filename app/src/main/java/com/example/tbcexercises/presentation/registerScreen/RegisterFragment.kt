package com.example.tbcexercises.presentation.registerScreen


import androidx.fragment.app.viewModels
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentRegisterBinding
import com.example.tbcexercises.util.BaseFragment
import com.example.tbcexercises.util.Result
import com.example.tbcexercises.util.toast


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: RegisterViewModel by viewModels()
    override fun start() {

    }

    override fun listeners() {
        binding.btnRegister.setOnClickListener {
            register()
        }

    }

    private fun register() {
        val email = binding.etEmail.text.toString()
        val username = binding.etUserName.toString()
        val password = binding.etPassword.text.toString()

        val validatedAuth =
            viewModel.validateFields(
                email = email,
                password = password,
                username = username,
                onException = { toast(it) })

        when (validatedAuth) {
            is Result.Error -> toast(validatedAuth.error)
            is Result.Success<*> -> toast(getString(R.string.successfully_signed_in_congrats))
        }
    }
}
