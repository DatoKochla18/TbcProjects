package com.example.tbcexercises

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.tbcexercises.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.GoogleAuthProvider
import kotlin.math.log


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var firebaseAuth: FirebaseAuth

    private val TAG = "GoogleFragment"
    private val RC_SIGN_IN = 9

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        setUp()
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

    private fun setUp() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        firebaseAuth = FirebaseAuth.getInstance()


        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

    }

    private fun listeners() {
        binding.btnArrowBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnSignIn.setOnClickListener {

            login()
        }

        binding.btnGoogle.setOnClickListener {
            signInGoogle()
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

    // Code Copied From Firebase Documentation
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.d(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    parentFragmentManager.popBackStack(
                        null, FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    navigateToFragment(HomeFragment())
                } else {

                    Log.d("GoogleSignIn", "Sign in Error")
                }
            }
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
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