package com.example.tbcexercises.presentation.screen.bottom_sheet_from_account

import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.databinding.FragmentFromAccountBinding
import com.example.tbcexercises.presentation.base.BaseBottomSheetDialog
import com.example.tbcexercises.presentation.extension.collectLatestFlow
import com.example.tbcexercises.presentation.extension.showSnackBar
import com.example.tbcexercises.presentation.screen.common.card_account_adapter.CardAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FromAccountFragment :
    BaseBottomSheetDialog<FragmentFromAccountBinding>(FragmentFromAccountBinding::inflate) {
    private val viewmodel: FromAccountViewModel by viewModels()
    private val cardAccountAdapter by lazy {
        CardAdapter(
            onClick = {
                Log.d("executed", "executed")
                val result = Bundle().apply {
                    putParcelable("from_account", it)
                }
                parentFragmentManager.setFragmentResult("from_requestKey", result)
                findNavController().navigateUp()
            }
        )
    }

    override fun start() {
        viewmodel.onEvent(FromAccountEvent.GetCards)

        binding.rvCards.apply {
            adapter = cardAccountAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        collectLatestFlow(viewmodel.uiState) { updateUiState(it) }
        collectLatestFlow(viewmodel.sideEffect) { getSideEffects(it) }

    }


    override fun listeners() {
    }

    private fun getSideEffects(sideEffect: FromAccountSideEffect) {
        when (sideEffect) {
            is FromAccountSideEffect.ShowToast -> binding.root.showSnackBar(getString(sideEffect.message))
        }
    }

    private fun updateUiState(uiState: FromAccountUiState) {
        binding.progressBar.isVisible = uiState.isLoading

        cardAccountAdapter.submitList(uiState.cards)
    }

}