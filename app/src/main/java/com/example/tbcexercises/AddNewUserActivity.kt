package com.example.tbcexercises

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcexercises.databinding.ActivityAddNewUserBinding

class AddNewUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddNewUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        listeners()
    }

    private fun listeners() {
        binding.btnConfirm.setOnClickListener {
            saveUser()
        }
    }

    private fun saveUser() {
        val id = binding.etId.text.toString()

        val firstName = binding.etFirstName.text.toString().lowercase()
        val lastName = binding.etLastName.text.toString().lowercase()
        val email = binding.etEmail.text.toString().lowercase()
        val birthday = binding.etBirthday.text.toString()
        val address = binding.etAddress.text.toString().lowercase()
        val desc = binding.etdesc.text.toString().lowercase()

        if (firstName.isNotEmpty() &&
            lastName.isNotEmpty() &&
            email.isNotEmpty() &&
            birthday.isNotEmpty() &&
            address.isNotEmpty() &&
            desc.isEmpty()
        ) {

            val data = User(
                id = id.toInt(),
                firstName = firstName,
                lastName = lastName,
                email = email,
                birthday = birthday,
                address = address,
                desc = desc
            )

            val resultIntent = Intent()
            resultIntent.putExtra("data", data)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()

        } else {
            Toast.makeText(
                this,
                getString(R.string.field_should_not_be_empty), Toast.LENGTH_SHORT
            ).show()
        }
    }
}