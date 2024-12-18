package com.example.tbcexercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.tbcexercises.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlin.math.log


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
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
        binding.btnArrowBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnSignIn.setOnClickListener {

            login()
        }

        binding.txtSignUp.setOnClickListener {
            val fragmentExits = parentFragmentManager.findFragmentByTag(REGISTER_TAG)
            if (fragmentExits != null) {
                parentFragmentManager.popBackStack(REGISTER_TAG, 0)
            } else {
                navigateToFragment(RegisterFragment(), addToBackStack = true, tag = REGISTER_TAG)
            }
        }


    }

    private fun login() {
        val email: String = binding.etEmail.text.toString()
        val password: String = binding.etPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            binding.txtEmailError.text = ""
            binding.txtPasswordError.text = ""
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    parentFragmentManager.popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    navigateToFragment(HomeFragment())
                }

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


    private fun navigateToFragment(
        fragment: Fragment,
        addToBackStack: Boolean = false,
        tag: String? = null,

        ) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment, tag)
            if (addToBackStack) addToBackStack(tag)

            commit()
        }
    }

}