package com.example.tbcexercises

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcexercises.databinding.ActivityRegisterSecondBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.userProfileChangeRequest

class RegisterSecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterSecondBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterSecondBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        listeners()
    }


    private fun listeners() {
        binding.btnSignUp.setOnClickListener {
            register()
        }


        binding.txtTermsOfService.setOnClickListener {
            val intent = Intent(this, TermsOfServiceActivity::class.java)
            startActivity(intent)
        }

        binding.btnArrowBack.setOnClickListener {
            finish()
        }
    }

    private fun register() {
        val email: String = intent.getStringExtra("email").toString()
        val password: String = intent.getStringExtra("password").toString()

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
                    val intent = Intent(this, HomeScreenActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
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