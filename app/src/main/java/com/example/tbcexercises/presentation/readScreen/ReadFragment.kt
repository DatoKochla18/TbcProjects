package com.example.tbcexercises.presentation.readScreen


import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.base.BaseFragment
import com.example.tbcexercises.databinding.FragmentReadBinding
import com.example.tbcexercises.utils.collectLastState
import com.example.tbcexercises.utils.toast

class ReadFragment : BaseFragment<FragmentReadBinding>(FragmentReadBinding::inflate) {

    private val viewModel: ReadScreenViewModel by viewModels()

    override fun start() {
        collectLastState(viewModel.readUserState) {

            it.isSuccessful.let { user ->
                binding.txtResult.text = user?.toString() ?: ""
            }

            if (it.error != null) {
                toast(requireContext().getString(it.error))
            }
        }
    }

    override fun listeners() {
        binding.btnSearchUser.setOnClickListener {
            val email = binding.etEmail.text.toString()

            viewModel.readUser(email)
        }


        binding.btnAddUser.setOnClickListener {
            findNavController().navigate(ReadFragmentDirections.actionReadFragmentToSaveFragment())
        }
    }
}