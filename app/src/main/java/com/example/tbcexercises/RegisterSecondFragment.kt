package com.example.tbcexercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tbcexercises.databinding.FragmentRegisterSecondBinding

class RegisterSecondFragment : Fragment() {
    private lateinit var binding: FragmentRegisterSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterSecondBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}