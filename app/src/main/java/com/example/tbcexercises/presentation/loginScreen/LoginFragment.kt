package com.example.tbcexercises.presentation.loginScreen


import androidx.fragment.app.viewModels
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentLoginBinding
import com.example.tbcexercises.util.BaseFragment
import com.example.tbcexercises.util.Result
import com.example.tbcexercises.util.toast


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()
    override fun start() {

    }

    override fun listeners() {
        binding.btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = binding.etUserName.text.toString()
        val password = binding.etPassword.text.toString()

        val validatedAuth =
            viewModel.validateFields(
                email,
                password,
                onException = { toast(it) })  // i like this way no having val in when

        when (validatedAuth) {
            is Result.Error -> toast(validatedAuth.error)
            is Result.Success<*> -> toast(getString(R.string.successfully_signed_in_congrats))
        }
    }
}
