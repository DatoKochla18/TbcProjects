package com.example.tbcexercises.presentation.homeScreen


import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.base.BaseFragment
import com.example.tbcexercises.databinding.FragmentHomeBinding
import com.example.tbcexercises.utils.exntension.dataStore
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {


    override fun start() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                requireContext().dataStore.data.map { it.email }.collectLatest { email ->
                    binding.txtEmail.text = email
                }
            }
        }
    }

    override fun listeners() {
        binding.btnLogOut.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    setSession(false, "")
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNavigation())
                }
            }
        }
    }

    private suspend fun setSession(rememberMe: Boolean, email: String) {
        requireContext().dataStore.updateData {
            it.copy(rememberMe = rememberMe, email = email)
        }
    }

}