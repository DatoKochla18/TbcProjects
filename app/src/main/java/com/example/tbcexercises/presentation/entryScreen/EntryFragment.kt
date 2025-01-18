package com.example.tbcexercises.presentation.entryScreen

import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.databinding.FragmentEntryBinding
import com.example.tbcexercises.util.BaseFragment


class EntryFragment : BaseFragment<FragmentEntryBinding>(FragmentEntryBinding::inflate) {
    override fun start() {

    }

    override fun listeners() {
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(EntryFragmentDirections.actionEntryFragmentToLoginFragment())
        }
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(EntryFragmentDirections.actionEntryFragmentToRegisterFragment())
        }

    }

}