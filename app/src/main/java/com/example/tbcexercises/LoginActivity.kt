package com.example.tbcexercises

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcexercises.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        listeners()
    }

    private fun listeners() {
        binding.btnArrowBack.setOnClickListener {
            Log.d("Login", "Clicked")
            finish()
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
                    val intent = Intent(this, HomeScreenActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()

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