package com.example.tbcexercises.presentation.screen.bottom_sheet_to_account

import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentToAccountBinding
import com.example.tbcexercises.presentation.base.BaseBottomSheetDialog
import com.example.tbcexercises.presentation.extension.collectLatestFlow
import com.example.tbcexercises.presentation.extension.showSnackBar
import com.example.tbcexercises.presentation.screen.common.card_account_adapter.CardAdapter
import com.example.tbcexercises.presentation.util.CardFindType
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ToAccountFragment :
    BaseBottomSheetDialog<FragmentToAccountBinding>(FragmentToAccountBinding::inflate) {
    private val viewmodel: ToAccountViewModel by viewModels()
    private val cardAdapter: CardAdapter by lazy {
        CardAdapter(onClick = {
            viewmodel.onEvent(ToAccountUiEvent.OnAccountClick(it))
        })
    }

    override fun start() {
        binding.rvCards.apply {
            adapter = cardAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        collectLatestFlow(viewmodel.uiState) { updateUiState(it) }

        collectLatestFlow(viewmodel.sideEffect) {
            when (it) {
                is ToAccountSideEffect.ShowError -> binding.root.showSnackBar(getString(it.message))
                is ToAccountSideEffect.SuccessfulCard -> {
                    val result = Bundle().apply {
                        putParcelable("to_account", it.account)
                    }
                    parentFragmentManager.setFragmentResult("to_requestKey", result)
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun listeners() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            searchTypeHelper(checkedId)
        }

        binding.etSearchField.doAfterTextChanged { editable ->
            viewmodel.onEvent(ToAccountUiEvent.UpdateText(editable.toString()))
        }
        binding.btnSearch.setOnClickListener {
            viewmodel.onEvent(ToAccountUiEvent.GetCards(binding.etSearchField.text.toString()))
        }
    }

    private fun updateUiState(uiState: ToAccountUiState) {
        Log.d("state", uiState.toString())
        binding.apply {
            txtError.text = uiState.validationError?.let { getString(it) }
            txtError.isVisible = uiState.validationError != null
            btnSearch.isEnabled = uiState.isButtonEnabled
            progressBar.isVisible = uiState.isLoading

            cardAdapter.submitList(uiState.cards.toList())
        }
    }

    private fun searchTypeHelper(checkedId: Int) {
        when (checkedId) {
            R.id.rbAccountNumber -> viewmodel.onEvent(
                ToAccountUiEvent.CardFindingByTypeChanged(
                    CardFindType.AccountNumber(
                        binding.etSearchField.text.toString()
                    )
                )
            )

            R.id.rbPhoneNumber -> viewmodel.onEvent(
                ToAccountUiEvent.CardFindingByTypeChanged(
                    CardFindType.PhoneNumber(
                        binding.etSearchField.text.toString()
                    )
                )
            )

            R.id.rbPersonalNumber -> viewmodel.onEvent(
                ToAccountUiEvent.CardFindingByTypeChanged(
                    CardFindType.PersonalNumber(
                        binding.etSearchField.text.toString()
                    )
                )
            )
        }
    }
}