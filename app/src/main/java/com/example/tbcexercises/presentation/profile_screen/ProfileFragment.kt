package com.example.tbcexercises.presentation.profile_screen

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.databinding.FragmentProfileBinding
import com.example.tbcexercises.utils.exntension.collectLastState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

    override fun start() {
        collectLastState(viewModel.emailFlow) { email ->
            binding.txtEmail.text = email
        }

    }

    override fun listeners() {
        binding.btnLogOut.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.clearUserSession()
                    findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToNavigation())
                }
            }
        }
    }

}