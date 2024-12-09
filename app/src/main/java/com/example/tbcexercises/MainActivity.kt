package com.example.tbcexercises

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        val anagramData = mutableListOf("ainapa", "anapaia", "napaia", "meats", "steam")
        val anagram = AnagramDecoder()


        binding.btnSave.setOnClickListener {
            if (!binding.etAnagram.text.toString().contains(",")) {
                anagramData.add(binding.etAnagram.text.toString())
            } else {
                for (element in binding.etAnagram.text.toString().split(",")) {
                    anagramData.add(element)
                }
            }
            binding.etAnagram.text.clear()
        }

        binding.btnClear.setOnClickListener {
            anagramData.clear()
            binding.txtResult.text = ""
            binding.etAnagram.text.clear()
        }


        binding.btnOutPut.setOnClickListener {
            val result = anagram.groupAnagrams(anagramData).toString()
            binding.txtResult.text = result.substring(1, result.length - 1)
        }

        binding.btnConnectTransfer.setOnClickListener {
            Log.d("Ar vici mara viswavli!!!", "Hello I am hacker from tbc it academy")
        }
    }
}