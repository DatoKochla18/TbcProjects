package com.example.tbcexercises.presentation.entryScreen

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.base.BaseFragment
import com.example.tbcexercises.databinding.FragmentEntryBinding
import com.example.tbcexercises.presentation.entryScreen.dialogAdapter.DialogAdapter
import com.example.tbcexercises.presentation.entryScreen.passwordAdapter.PasswordListAdapter
import com.example.tbcexercises.util.collectLastState
import com.example.tbcexercises.util.toast


class EntryFragment : BaseFragment<FragmentEntryBinding>(FragmentEntryBinding::inflate) {

    private val viewModel: EntryScreenViewModel by viewModels()

    private val dialogAdapter: DialogAdapter by lazy {
        DialogAdapter(viewModel.dialogs,
            onDialogNumberClick = { dialog -> viewModel.appendPassword(dialog.textOnView) },
            onDialogBackSpaceClick = { viewModel.deletePassword() })
    }

    private val passwordAdapter: PasswordListAdapter by lazy {
        PasswordListAdapter()
    }

    override fun start() {
        setUpDialogAdapter()
        setUpPasswordAdapter()
        collectLastState(viewModel.uiState) { state ->
            passwordAdapter.submitList(state.toList())
        }

        collectLastState(viewModel.answerFlow) { message ->
            toast(getString(message))
        }

    }

    override fun listeners() {

    }

    private fun setUpDialogAdapter() {
        binding.rvDialog.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = dialogAdapter
        }
    }

    private fun setUpPasswordAdapter() {
        binding.rvPassword.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            adapter = passwordAdapter
        }
    }

}