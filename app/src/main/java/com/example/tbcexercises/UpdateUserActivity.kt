package com.example.tbcexercises

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcexercises.databinding.ActivityUpdateUserBinding

class UpdateUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateUserBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        listeners()

    }

    private fun listeners() {

        binding.btnApprovedUser.setOnClickListener {
            update()
        }

    }

    private fun update() {
        val emails: List<String> =
            intent.getStringArrayListExtra("emails")?.toList() ?: mutableListOf()
        val email = binding.etEmail.text.toString()
        if (email in emails) {
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val age = binding.etAge.text.toString().toInt()

            val newUser =
                User(email = email, firstName = firstName, lastName = lastName, age = age)
            val sendIntent = Intent().apply {
                putExtra("user", newUser)
            }

            setResult(Activity.RESULT_OK, sendIntent)
            finish()
        } else {
            binding.txtEmailNameError.text = resources.getString(R.string.userDontExits)
        }
    }

}