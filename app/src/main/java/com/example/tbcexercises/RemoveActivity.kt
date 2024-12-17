package com.example.tbcexercises

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcexercises.databinding.ActivityRemoveBinding

class RemoveActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRemoveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRemoveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        listeners()

    }

    private fun listeners() {
        binding.btnApproved.setOnClickListener {
            remove()
        }
    }

    private fun remove() {
        val email = binding.etEmail.text.toString()

        if (intent.getStringArrayListExtra("emails")!!.contains(email)) {

            val sendIntent = Intent().apply {
                putExtra("email", email)
            }

            setResult(Activity.RESULT_OK, sendIntent)
            finish()
        } else {
            binding.txtEmailNameError.text = resources.getString(R.string.userDontExits)
        }
    }
}
