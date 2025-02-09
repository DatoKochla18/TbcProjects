package com.example.tbcexercises.presentation.profileScreen

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.data.local.dataStore.UserManager
import com.example.tbcexercises.data.local.dataStore.dataStore
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.databinding.FragmentProfileBinding
import com.example.tbcexercises.utils.exntension.collectLastState
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override fun start() {
        collectLastState(requireContext().dataStore.data.map { it.email }) { email ->
            binding.txtEmail.text = email
        }

    }

    override fun listeners() {
        binding.btnLogOut.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    UserManager.setSession(false, "")
                    findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToNavigation())
                }
            }
        }
    }

}