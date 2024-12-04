package com.example.tbcexercises

import android.content.Context
import android.os.Bundle
import android.util.Log
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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val validation: Validation = Validation(this)
        //EditText fields
        val etEmail: EditText = findViewById(R.id.EtEmail)
        val etUserName: EditText = findViewById(R.id.EtUsername)
        val etLastName: EditText = findViewById(R.id.EtLastName)
        val etFirstName: EditText = findViewById(R.id.EtFirstName)
        val etAge: EditText = findViewById(R.id.EtAge)

        //text Field For Errors
        val txtEmail: TextView = findViewById(R.id.txEmailError)
        val txtUserName: TextView = findViewById(R.id.txUsernamError)
        val txtFirstName: TextView = findViewById(R.id.txFirstNameError)
        val txtLastName: TextView = findViewById(R.id.txLastNameError)
        val txtAge: TextView = findViewById(R.id.txAgeError)

        //text fields after saving click
        val txtResultEmail: TextView = findViewById(R.id.txtResultEmail)
        val txtResultUserName: TextView = findViewById(R.id.txtResultUserName)
        val txtResultFullName: TextView = findViewById(R.id.txtResultFullName)
        val txtResultAge: TextView = findViewById(R.id.txtResultAge)

        //Buttons
        val btnSave: Button = findViewById(R.id.btnSave)
        val btnClear: Button = findViewById(R.id.btnClear)
        val btnAgain: Button = findViewById(R.id.btnAgain)



        btnClear.setOnLongClickListener {
            txtEmail.text = ""
            txtAge.text = ""
            txtLastName.text = ""
            txtUserName.text = ""
            txtFirstName.text = ""

            etEmail.text.clear()
            etAge.text.clear()
            etLastName.text.clear()
            etUserName.text.clear()
            etFirstName.text.clear()
            true
        }

        btnAgain.setOnClickListener {
            etEmail.visibility = View.VISIBLE
            etAge.visibility = View.VISIBLE
            etFirstName.visibility = View.VISIBLE
            etLastName.visibility = View.VISIBLE
            etUserName.visibility = View.VISIBLE

            txtAge.visibility = View.VISIBLE
            txtEmail.visibility = View.VISIBLE
            txtLastName.visibility = View.VISIBLE
            txtFirstName.visibility = View.VISIBLE
            txtUserName.visibility = View.VISIBLE

            btnSave.visibility = View.VISIBLE
            btnClear.visibility = View.VISIBLE

            txtResultEmail.visibility = View.GONE

            txtResultUserName.visibility = View.GONE

            txtResultFullName.visibility = View.GONE

            txtResultAge.visibility = View.GONE

            btnAgain.visibility = View.GONE

            //for clearing
            txtEmail.text = ""
            txtAge.text = ""
            txtLastName.text = ""
            txtUserName.text = ""
            txtFirstName.text = ""

            etEmail.text.clear()
            etAge.text.clear()
            etLastName.text.clear()
            etUserName.text.clear()
            etFirstName.text.clear()

        }
        btnSave.setOnClickListener {
            val resultEmail: Result = validation.validateEmail(etEmail.text.toString())
            txtEmail.text = when (resultEmail) {
                is Result.Success -> ""
                is Result.Error -> resultEmail.error
            }
            val resultUserName: Result = validation.validateUserName(etUserName.text.toString())
            txtUserName.text = when (resultUserName) {
                is Result.Success -> ""
                is Result.Error -> resultUserName.error
            }
            val resultFirstName: Result = validation.validateFirstName(etFirstName.text.toString())
            txtFirstName.text = when (resultFirstName) {
                is Result.Success -> ""
                is Result.Error -> resultFirstName.error
            }

            val resultLastName: Result = validation.validateLastName(etLastName.text.toString())
            txtLastName.text = when (resultLastName) {
                is Result.Success -> ""
                is Result.Error -> resultLastName.error
            }
            val resultAge: Result = validation.validateAge(etAge.text.toString())
            txtAge.text = when (resultAge) {
                is Result.Success -> ""
                is Result.Error -> resultAge.error
            }

            if (resultEmail is Result.Success && resultUserName is Result.Success && resultFirstName is Result.Success && resultLastName is Result.Success && resultAge is Result.Success) {
                etEmail.visibility = View.GONE
                etAge.visibility = View.GONE
                etFirstName.visibility = View.GONE
                etLastName.visibility = View.GONE
                etUserName.visibility = View.GONE

                txtAge.visibility = View.GONE
                txtEmail.visibility = View.GONE
                txtLastName.visibility = View.GONE
                txtFirstName.visibility = View.GONE
                txtUserName.visibility = View.GONE

                btnSave.visibility = View.GONE
                btnClear.visibility = View.GONE

                txtResultEmail.visibility = View.VISIBLE
                txtResultEmail.text = "Email: " + etEmail.text

                txtResultUserName.visibility = View.VISIBLE
                txtResultUserName.text = "UserName " + etUserName.text

                txtResultFullName.visibility = View.VISIBLE
                txtResultFullName.text =
                    "FullName: " + etFirstName.text.toString() + " " + etLastName.text.toString()

                txtResultAge.visibility = View.VISIBLE
                txtResultAge.text = "Age: " + etAge.text

                btnAgain.visibility = View.VISIBLE


            }


        }
    }
}
