package com.example.tbcexercises

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tbcexercises.databinding.FragmentEntryBinding
import com.example.tbcexercises.databinding.FragmentLoginBinding
import com.example.tbcexercises.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth


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
            val email: String = binding.etLoginEmail.text.toString()
            val password: String = binding.etLoginPassword.text.toString()
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val homeScreenFragment = HomeScreenFragment()
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, homeScreenFragment)
                        commit()
                    }
                }
            }

        }
    }

}