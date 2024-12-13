package com.example.tbcexercises

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tbcexercises.databinding.FragmentEntryBinding
import com.example.tbcexercises.databinding.FragmentRegisterBinding
import com.example.tbcexercises.databinding.FragmentRegisterSecondBinding


class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private val registerSecondFragment = RegisterSecondFragment()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentRegisterBinding.bind(
                super.onCreateView(
                    inflater,
                    container,
                    savedInstanceState
                )!!
            )
        listeners()

        return binding.root
    }


    private fun listeners() {
        binding.btnRegisterNext.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, registerSecondFragment)
                addToBackStack(null)
                commit()
            }
        }

        binding.btnArrowBack.setOnClickListener {
            Log.d("Register", "Clicked")
            parentFragmentManager.popBackStack()

        }


    }
}