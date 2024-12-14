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


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val registerSecondFragment = RegisterSecondFragment()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            FragmentRegisterBinding.inflate(layoutInflater, container, false)
        listeners()

        return binding.root
    }


    private fun listeners() {
        binding.btnRegisterNext.setOnClickListener {
            val email: String = binding.etRegisterEmail.text.toString()
            val password: String = binding.etRegisterPassword.text.toString()

            val bundle = Bundle()

            bundle.putString("email", email)
            bundle.putString("password", password)


            registerSecondFragment.arguments = bundle
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