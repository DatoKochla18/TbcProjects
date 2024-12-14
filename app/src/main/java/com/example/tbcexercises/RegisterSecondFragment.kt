package com.example.tbcexercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tbcexercises.databinding.FragmentRegisterSecondBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

class RegisterSecondFragment : Fragment() {
    private lateinit var binding: FragmentRegisterSecondBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private val termsOfServiceFragment = TermsOfServiceFragment()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterSecondBinding.inflate(layoutInflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        listeners()
        return binding.root
    }


    private fun listeners() {
        binding.btnSignUp.setOnClickListener {
            val email: String = arguments?.getString("email").toString()
            val password: String = arguments?.getString("password").toString()

            val username: String = binding.etUserName.text.toString()

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {

                    val updatedProfile = userProfileChangeRequest {
                        displayName = username
                    }
                    firebaseAuth.currentUser?.updateProfile(updatedProfile)
                    val homeScreenFragment = HomeScreenFragment()
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, homeScreenFragment)
                        commit()
                    }
                }
            }
        }


        binding.txtTermsOfService.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, termsOfServiceFragment)
                addToBackStack(null)
                commit()
            }
        }

        binding.btnArrowBack.setOnClickListener {
            parentFragmentManager.popBackStack()

        }
    }
}