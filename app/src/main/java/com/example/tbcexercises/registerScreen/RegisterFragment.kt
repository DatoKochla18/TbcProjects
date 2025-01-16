package com.example.tbcexercises.registerScreen

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.util.BaseFragment
import com.example.tbcexercises.registerScreen.parentRecycle.CardAdapter
import com.example.tbcexercises.R
import com.example.tbcexercises.util.Result
import com.example.tbcexercises.databinding.FragmentRegisterBinding
import com.example.tbcexercises.util.toast


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val cardAdapter by lazy {
        CardAdapter(onTextChanged = { id, text ->
            viewModel.updateView(id, text ?: "")
        })
    }
    private val viewModel: RegisterViewModel by viewModels()

    override fun start() {
        binding.rvCards.apply {
            adapter = cardAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        cardAdapter.submitList(viewModel.decodedData)

    }

    override fun listeners() {
        binding.btnRegister.setOnClickListener {
            register()
        }
    }


    private fun register() {
        val result = viewModel.checkForRequiredFields()

        when (result) {
            is Result.OnError -> {
                toast(getString(R.string.field_missing, result.error) )
            }

            is Result.OnSuccess -> navigate(result.profile)
        }
    }

    private fun navigate(profile: String) {
        findNavController().navigate(
            RegisterFragmentDirections.actionRegisterFragmentToProfileFragment(
                profile
            )
        )
    }

}