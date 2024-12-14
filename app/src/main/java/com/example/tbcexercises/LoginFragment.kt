package com.example.tbcexercises

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.tbcexercises.databinding.FragmentEntryBinding
import com.example.tbcexercises.databinding.FragmentLoginBinding
import com.example.tbcexercises.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentLoginBinding.inflate(layoutInflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        listeners()

        return binding.root
    }

    private fun listeners() {
        binding.btnArrowBack.setOnClickListener {
            Log.d("Login", "Clicked")
            parentFragmentManager.popBackStack()
        }

        binding.btnLogin.setOnClickListener {
            login()

        }
    }

    private fun login() {
        val email: String = binding.etLoginEmail.text.toString()
        val password: String = binding.etLoginPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            binding.txtEmailError.text = ""
            binding.txtPasswordError.text = ""
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val homeScreenFragment = HomeScreenFragment()
                    parentFragmentManager.popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )/* I;m clearing backStack because when i login succesfully, and head to homescreen and
                        then tap back button,if i did not cleared backtack,for   background it will be homescreen
                    */
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, homeScreenFragment)
                        commit()
                    }
                }
                Log.d("ExceptionForLogin", it.exception.toString())
                if (it.exception is FirebaseAuthInvalidCredentialsException) {
                    binding.txtEmailError.text = getString(R.string.user_not_found)
                }
            }
        } else {
            binding.txtEmailError.text = when {
                email.isEmpty() -> getString(R.string.ValidEmptyFields)
                else -> ""
            }
            binding.txtPasswordError.text = when {
                password.isEmpty() -> getString(R.string.ValidEmptyFields)
                else -> ""
            }

        }

    }
}