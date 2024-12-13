package com.example.tbcexercises

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tbcexercises.databinding.FragmentEntryBinding
import com.example.tbcexercises.databinding.FragmentLoginBinding
import com.example.tbcexercises.databinding.FragmentRegisterBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentLoginBinding.inflate(layoutInflater,container,false)
        listeners()
        return binding.root
    }

    private fun listeners() {
        binding.btnArrowBack.setOnClickListener {
            Log.d("Login", "Clicked")
            parentFragmentManager.popBackStack()
        }
    }

}