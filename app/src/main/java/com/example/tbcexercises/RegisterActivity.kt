package com.example.tbcexercises

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcexercises.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)

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
        binding.btnRegisterNext.setOnClickListener {
            sendDataToRegister()
        }

        binding.btnArrowBack.setOnClickListener {
            Log.d("Register", "Clicked")
            finish()
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

            val intent = Intent(this, RegisterSecondActivity::class.java)

            intent.putExtra("email", email)
            intent.putExtra("password", password)


            startActivity(intent)
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