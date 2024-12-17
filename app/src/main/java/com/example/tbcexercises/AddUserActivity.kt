package com.example.tbcexercises

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcexercises.databinding.ActivityAddUserBinding
import kotlin.time.Duration

class AddUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddUserBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listener()
    }

    private fun listener() {
        binding.btnApprovedUser.setOnClickListener {
            add()
        }

    }

    private fun add() {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val age = binding.etAge.text.toString()
        val email = binding.etEmail.text.toString()

        val firstNameResult = validateName(firstName)
        val lastNameResult = validateName(lastName)
        val ageResult = validateAge(age)
        val emailResult = validateEmail(email)

        if (firstNameResult is Result.Success &&
            lastNameResult is Result.Success &&
            ageResult is Result.Success &&
            emailResult is Result.Success
        ) {
            val newUser =
                User(
                    firstName = firstName,
                    lastName = lastName,
                    age = age.toInt(),
                    email = email
                )

            val intent = Intent().apply {
                putExtra("user", newUser)
            }

            setResult(Activity.RESULT_OK, intent)
            Toast.makeText(this,resources.getString(R.string.userAdded),Toast.LENGTH_SHORT).show()
            finish()
        } else {
            binding.txtFirstNameError.text = when (firstNameResult) {
                is Result.Success -> ""
                is Result.Error -> firstNameResult.error
            }
            binding.txtLastNameError.text = when (lastNameResult) {
                is Result.Success -> ""
                is Result.Error -> lastNameResult.error
            }
            binding.txtAgeError.text = when (ageResult) {
                is Result.Success -> ""
                is Result.Error -> ageResult.error
            }
            binding.txtEmailNameError.text = when (emailResult) {
                is Result.Success -> ""
                is Result.Error -> emailResult.error
            }

        }
    }

    // Code copied from Task4
    private fun validateEmail(text: String): Result {
        return when {
            text.isEmpty() -> Result.Error(resources.getString(R.string.ValidEmptyFields))
            intent.getStringArrayListExtra("emails")!!.toList()
                .contains(text) -> Result.Error(resources.getString(R.string.userExits))

            text[0].toString() == "." || text[text.length - 1].toString() == "." -> Result.Error(
                resources.getString(R.string.ValidEmailDots)
            )

            text.any {
                it in listOf(
                    '+', '-', '&', '|', '!', '(', ')', '{', '}', '[', ']', '^', '~', '*', '?', ':'
                )
            } -> Result.Error(resources.getString(R.string.ValidEmailCantContainSpecialChars))

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

    private fun validateAge(age: String): Result {
        return when {
            age.isEmpty() -> Result.Error(resources.getString(R.string.ValidEmptyFields))

            (age.toInt() < 1) || (age.toInt() > 150) -> Result.Error(
                resources.getString(R.string.ValidAge)
            )

            else -> Result.Success()

        }
    }

    private fun validateName(name: String): Result {
        return when {
            name.isEmpty() -> Result.Error(resources.getString(R.string.ValidEmptyFields))
            (name.length < 2) -> Result.Error(resources.getString(R.string.ValidName))

            else -> Result.Success()

        }
    }


}