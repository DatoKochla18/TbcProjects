package com.example.tbcexercises.presentation.saveScreen


import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.base.BaseFragment
import com.example.tbcexercises.databinding.FragmentSaveBinding
import com.example.tbcexercises.utils.collectLastState
import com.example.tbcexercises.utils.toast


class SaveFragment : BaseFragment<FragmentSaveBinding>(FragmentSaveBinding::inflate) {

    private val viewModel: SaveScreenViewModel by viewModels()

    override fun start() {
        collectLastState(viewModel.saveUserState) {
            showLoadingScreen(it.isLoading)
            if (it.isSuccessful) {
                onSuccess()
            }
            if (it.error != null) {
                toast(requireContext().getString(it.error))
            }
        }
    }

    override fun listeners() {
        binding.btnSaveUser.setOnClickListener {
            saveUser()
        }
    }

    private fun saveUser() {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val email = binding.etEmail.text.toString()

        viewModel.saveUser(firstName = firstName, lastName = lastName, email = email)
    }

    private fun showLoadingScreen(loading: Boolean) {
        binding.apply {
            btnSaveUser.isVisible = !loading
            etEmail.isVisible = !loading
            etFirstName.isVisible = !loading
            etLastName.isVisible = !loading

            progressBar.isVisible = loading
        }
    }

    private fun onSuccess() {
        findNavController().navigateUp()
    }
}