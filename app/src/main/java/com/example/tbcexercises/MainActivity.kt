package com.example.tbcexercises

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcexercises.databinding.ActivityMainBinding
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //val data = mutableListOf<User>()
    private val userData = mutableListOf(
        User(firstName = "dato", lastName = "kochla", 19, "dato@gmail.com"),
        User(firstName = "gio", lastName = "kochla", 19, "gio@gmail.com")
    )
    private var removedUser: Int = 0


    private val addUserLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val user = result.data?.getParcelableExtra<User>("user")
                userData.add(user!!)
            }

        }
    private val removeUserLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val email = result.data?.getStringExtra("email")
                userData.removeIf {
                    it.email == email
                }
                removedUser++
            }

        }
    private val updateUserLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val user = result.data?.getParcelableExtra<User>("user")

                val elementIndex = userData.indexOfFirst { it.email == user!!.email }
                userData[elementIndex] = userData[elementIndex].copy(
                    firstName = user!!.firstName,
                    lastName = user.lastName,
                    age = user.age
                )
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

    override fun onResume() {
        super.onResume()
        binding.txtActiveUsers.text = userData.size.toString()
        binding.txtRemovedUsers.text = removedUser.toString()
    }

    private fun  listeners(){
        binding.btnUserAdd.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            intent.putExtra(
                "emails", ArrayList(userData.map { it.email })
            )
            addUserLauncher.launch(intent)
        }

        binding.btnUserRemove.setOnClickListener {
            val intent = Intent(this, RemoveActivity::class.java)
            intent.putExtra(
                "emails", ArrayList(userData.map { it.email })
            )
            removeUserLauncher.launch(intent)
        }


        binding.btnUserUpdate.setOnClickListener {

            val intent = Intent(this, UpdateUserActivity::class.java)
            intent.putExtra(
                "emails", ArrayList(userData.map { it.email })
            )
            updateUserLauncher.launch(intent)
        }
    }

}