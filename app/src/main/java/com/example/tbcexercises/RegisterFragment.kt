package com.example.tbcexercises

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tbcexercises.databinding.FragmentEntryBinding
import com.example.tbcexercises.databinding.FragmentRegisterBinding
import com.example.tbcexercises.databinding.FragmentRegisterSecondBinding


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerSecondFragment = RegisterSecondFragment()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding =
            FragmentRegisterBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun listeners() {
        binding.btnRegisterNext.setOnClickListener {
            sendDataToRegister()
        }

        binding.btnArrowBack.setOnClickListener {
            Log.d("Register", "Clicked")
            parentFragmentManager.popBackStack()

        }


    }

    private fun sendDataToRegister() {
        val email: String = binding.etRegisterEmail.text.toString()
        val password: String = binding.etRegisterPassword.text.toString()

        val emailResult = validateEmail(email)
        val passwordResult = validatePassword(password)

        if (emailResult is Result.Success && passwordResult is Result.Success) {
            binding.txtEmailError.text = ""
            binding.txtPasswordError.text = ""

            val bundle = Bundle()

            bundle.putString("email", email)
            bundle.putString("password", password)


            registerSecondFragment.arguments = bundle
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, registerSecondFragment)
                addToBackStack(null)
                commit()
            }
        } else {
            binding.txtEmailError.text = when (emailResult) {
                is Result.Success -> ""
                is Result.Error -> emailResult.error
            }

            binding.txtPasswordError.text = when (passwordResult) {
                is Result.Success -> ""
                is Result.Error -> passwordResult.error
            }
        }
    }

    // Code copied from Task4
    private fun validateEmail(text: String): Result {
        return when {
            text.isEmpty() -> Result.Error(resources.getString(R.string.ValidEmptyFields))
            text[0].toString() == "." || text[text.length - 1].toString() == "." -> Result.Error(
                resources.getString(R.string.ValidEmailDots)
            )

            text.any {
                it in listOf(
                    '+',
                    '-',
                    '&',
                    '|',
                    '!',
                    '(',
                    ')',
                    '{',
                    '}',
                    '[',
                    ']',
                    '^',
                    '~',
                    '*',
                    '?',
                    ':'
                )
            }
            -> Result.Error(resources.getString(R.string.ValidEmailCantContainSpecialChars))

            !text.contains("@") -> Result.Error(resources.getString(R.string.ValidEmailNotContainingSymbol))
            text.filter { it == '@' }
                .count() > 1 -> Result.Error(resources.getString(R.string.ValidEmailContainingMoreSymbol))

            !text.contains(".") -> Result.Error(resources.getString(R.string.ValidEmailShouldContainDot))
            text.substring(text.lastIndexOf("."), text.length).any { it.isDigit() } -> Result.Error(
                resources.getString(R.string.ValidEmailDomain)
            )

            text.contains(" ") -> Result.Error(resources.getString(R.string.ValidEmailSpaces))
            text.indexOf("@") > text.lastIndexOf(".") -> Result.Error(resources.getString(R.string.ValidEmailMissingDots))
            text.contains("@.") -> Result.Error(resources.getString(R.string.ValidEmailContainingSymbolAndDotConsecutive))

            text.contains("..") -> Result.Error(resources.getString(R.string.ValidEmailConsecutiveDots))
            else -> Result.Success()
        }

    }

    private fun validatePassword(password: String): Result {
        return when {
            password.length < 6 -> Result.Error(
                resources.getString(R.string.ValidPasswordShort)
            )

            else -> Result.Success()

        }
    }
}