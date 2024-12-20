package com.example.tbcexercises

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.example.tbcexercises.databinding.ActivityMainBinding

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
        ),
        User(
            id = 2,
            firstName = "stalini",
            lastName = "kochla",
            birthday = "17119437701641",
            address = "qiziyi",
            email = "stalini@beria.ru"
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

        if (searchText.isEmpty()) {
            binding.apply {
                txtNoUser.visibility = View.GONE
                btnAddNewUser.visibility = View.GONE
            }

        } else if (result.isEmpty()) {
            binding.apply {
                btnAddNewUser.visibility = View.VISIBLE
                txtNoUser.visibility = View.VISIBLE
                txtUserEmail.visibility = View.GONE
                txtUserAdress.visibility = View.GONE
                txtUserFirstname.visibility = View.GONE
                txtUserLastname.visibility = View.GONE
                txtUserBirthday.visibility = View.GONE
            }

        } else {
            binding.apply {
                btnAddNewUser.visibility = View.GONE
                txtNoUser.visibility = View.GONE
                txtUserEmail.visibility = View.VISIBLE
                txtUserAdress.visibility = View.VISIBLE
                txtUserFirstname.visibility = View.VISIBLE
                txtUserLastname.visibility = View.VISIBLE
                txtUserBirthday.visibility = View.VISIBLE

                txtUserEmail.text = result.first().email
                txtUserFirstname.text = result.first().firstName
                txtUserLastname.text = result.first().lastName
                txtUserAdress.text = result.first().address
                txtUserBirthday.text = result.first().birthday.toTextDate()

            }

        }
    }


}