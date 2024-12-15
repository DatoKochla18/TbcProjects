package com.example.tbcexercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.tbcexercises.databinding.FragmentRegisterSecondBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.userProfileChangeRequest

class RegisterSecondFragment : Fragment() {
    private var _binding: FragmentRegisterSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth

    private val termsOfServiceFragment = TermsOfServiceFragment()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterSecondBinding.inflate(layoutInflater, container, false)
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
        binding.btnSignUp.setOnClickListener {
            register()
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

    private fun register() {
        val email: String = arguments?.getString("email").toString()
        val password: String = arguments?.getString("password").toString()

        val username: String = binding.etUserName.text.toString()

        val userNameResult = validateUserName(username)

        when (userNameResult) {
            is Result.Success -> createUser(email, password, username)
            is Result.Error -> {
                binding.txtUserNameError.text = userNameResult.error
            }
        }
    }

    private fun createUser(email: String, password: String, username: String) {
        binding.txtUserNameError.text = ""
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {

                val updatedProfile = userProfileChangeRequest {
                    displayName = username
                }

                firebaseAuth.currentUser?.updateProfile(updatedProfile)?.addOnCompleteListener {

                    val homeScreenFragment = HomeScreenFragment()
                    parentFragmentManager.popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, homeScreenFragment)
                        commit()
                    }
                }
            }
            if (it.exception is FirebaseAuthUserCollisionException) {
                binding.txtUserNameError.text = resources.getString(R.string.ValidEmailInUse)
            }
        }
    }


    private fun validateUserName(userName: String): Result {
        return when {
            userName.length < 3 -> Result.Error(getString(R.string.ValidUserNameShort))
            else -> Result.Success()
        }


    }
}