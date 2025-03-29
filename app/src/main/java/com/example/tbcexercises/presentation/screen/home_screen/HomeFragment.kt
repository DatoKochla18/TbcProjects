package com.example.tbcexercises.presentation.screen.home_screen

import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.databinding.FragmentHomeBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun start() {
    }

    override fun listeners() {
        binding.itemFromAccountCard.root.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFromAccountFragment())
        }
    }

}