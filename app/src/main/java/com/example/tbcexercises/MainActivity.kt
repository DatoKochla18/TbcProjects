package com.example.tbcexercises

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.example.tbcexercises.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val data = mutableListOf(
        User(
            id = 1,
            firstName = "dato",
            lastName = "kochla",
            birthday = "1724647701641",
            address = "tbilisi",
            email = "dato@gmail.com"
        ), User(
            id = 2,
            firstName = "gio",
            lastName = "kochla",
            birthday = "1711947701641",
            address = "bodbe",
            email = "gio@gmail.com"
        )
    )

    private val addNewUserLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val user = result.data?.getParcelableExtra<User>("data")
                data.add(user!!)
                search()
            }

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        listeners()
    }

    private fun listeners() {

        binding.etSearchText.doAfterTextChanged {
            search()
        }

        binding.btnAddNewUser.setOnClickListener {
            val intent = Intent(this, AddNewUserActivity::class.java)
            addNewUserLauncher.launch(intent)

        }
    }

    private fun search() {
        val searchText = binding.etSearchText.text.toString()

        val result = data.filter { user ->
            (user.firstName == searchText ||
                    user.lastName == searchText ||

                    user.birthday.toTextDate().lowercase() == searchText.lowercase() ||
                    user.address == searchText ||
                    user.email == searchText
                    )
        }
        Log.d("userBirtday", searchText.lowercase())
        Log.d("user", data[0].birthday.toTextDate())

        if (result.isEmpty()) {
            binding.btnAddNewUser.visibility = View.VISIBLE
            binding.txtNoUser.visibility = View.VISIBLE
            binding.txtUserEmail.visibility = View.GONE
            binding.txtUserAdress.visibility = View.GONE
            binding.txtUserFirstname.visibility = View.GONE
            binding.txtUserLastname.visibility = View.GONE
            binding.txtUserBirthday.visibility = View.GONE

        } else {

            binding.btnAddNewUser.visibility = View.GONE
            binding.txtNoUser.visibility = View.GONE
            binding.txtUserEmail.visibility = View.VISIBLE
            binding.txtUserAdress.visibility = View.VISIBLE
            binding.txtUserFirstname.visibility = View.VISIBLE
            binding.txtUserLastname.visibility = View.VISIBLE
            binding.txtUserBirthday.visibility = View.VISIBLE

            binding.txtUserEmail.text = result.first().email
            binding.txtUserFirstname.text = result.first().firstName
            binding.txtUserLastname.text = result.first().lastName
            binding.txtUserAdress.text = result.first().address
            binding.txtUserBirthday.text = result.first().birthday.toTextDate()

        }
    }


}