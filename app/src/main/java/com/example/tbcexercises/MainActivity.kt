package com.example.tbcexercises

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginLeft
import androidx.core.view.setPadding
import com.example.tbcexercises.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val validation: Validation = Validation(this)
        val data: MutableMap<String, String> = mutableMapOf()

        binding.btnAddUser.setOnClickListener {
            val userName: String = binding.etFullName.text.toString()
            val email: String = binding.etEmailForAdding.text.toString()

            val userNameResult = validation.validateFullName(userName)
            val emailResult = validation.validateEmail(email,data.keys.toList())

            if (userNameResult is Result.Success && emailResult is Result.Success) {
                data[email] = userName

                binding.txtUserCount.text = "Users -> ${data.size}"

                binding.txtFullNameError.text = ""
                binding.txtEmailError.text = ""

                binding.etFullName.text?.clear()
                binding.etEmailForAdding.text?.clear()


            } else {
                binding.txtFullNameError.text = when (userNameResult) {
                    is Result.Error -> userNameResult.error
                    is Result.Success -> ""
                }
                binding.txtEmailError.text = when (emailResult) {
                    is Result.Error -> emailResult.error
                    is Result.Success -> ""
                }


            }
        }

        binding.getUser.setOnClickListener {
            val email: String = binding.etEmailForSearching.text.toString()
            val validatedEmail: Result = validation.validateEmail(email)
            when (validatedEmail) {
                is Result.Error -> {
                    binding.txtEmailErrorForSearching.text = validatedEmail.error
                    binding.txtSearchResult.text = ""
                }

                is Result.Success -> {
                    binding.txtEmailErrorForSearching.text = ""
                    binding.txtSearchResult.text =
                        if (data.get(email) == null) {
                            resources.getString(R.string.UserNotFound)
                        } else {
                            "Full Name ${data.get(email)} \nEmail $email "
                        }
                }
            }
        }


    }
}
