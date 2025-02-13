package com.example.tbcexercises.presentation.profileScreen

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.data.local.dataStore.UserManager
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.databinding.FragmentProfileBinding
import com.example.tbcexercises.utils.exntension.collectLastState
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    @Inject
    lateinit var userManager: UserManager


    override fun start() {
        collectLastState(userManager.emailFlow) { email ->
            binding.txtEmail.text = email
        }

    }

    override fun listeners() {
        binding.btnLogOut.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    userManager.setSession(false, "")
                    findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToNavigation())
                }
            }
        }
    }

}