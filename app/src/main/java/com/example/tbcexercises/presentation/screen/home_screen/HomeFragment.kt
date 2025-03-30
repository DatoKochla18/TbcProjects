package com.example.tbcexercises.presentation.screen.home_screen

import android.util.Log
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentHomeBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.extension.collectLatestFlow
import com.example.tbcexercises.presentation.extension.loadImg
import com.example.tbcexercises.presentation.model.Card
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewmodel: HomeViewmodel by viewModels()
    override fun start() {


        parentFragmentManager.setFragmentResultListener(
            "from_requestKey",
            this
        ) { _, bundle ->
            val fromAccount: Card? = bundle.getParcelable("from_account")
            fromAccount?.let {
                viewmodel.onEvent(HomeUiEvent.UpdateFromAccount(it))
            }
        }

        parentFragmentManager.setFragmentResultListener(
            "to_requestKey",
            this
        ) { _, bundle ->
            val toAccount: Card? = bundle.getParcelable("to_account")
            toAccount?.let {
                viewmodel.onEvent(HomeUiEvent.UpdateToAccount(it))
            }
        }
        collectLatestFlow(viewmodel.uiState) {
            updateUiState(it)
        }

    }

    private fun updateUiState(uiState: HomeUiState) {
        Log.d("homeuiState", uiState.toString())
        binding.apply {
            itemToMoneyValue.etEnterMoney.setText(uiState.moneyRight.toString())

            itemFromMoneyValue.root.isVisible = uiState.showSecondMoneyConverter

            progressBar.root.isVisible = uiState.isLoading

            itemToAccountCard.root.isVisible = !uiState.isLoading
            itemFromAccountCard.root.isVisible = !uiState.isLoading
            itemToMoneyValue.root.isVisible = !uiState.isLoading
            itemFromMoneyValue.root.isVisible = !uiState.isLoading

            itemToAccountCard.apply {
                uiState.toAccount?.let {
                    txtCardNumber.text = it.accountNumber
                    txtPrice.text = it.balance.toString()
                    txtMoneyType.text = binding.root.context.getString(R.string.cash)
                    imgCardLogo.loadImg(it.cardLogo)

                }
            }

            itemFromAccountCard.apply {
                uiState.fromAccount?.let {
                    txtCardNumber.text = it.accountNumber
                    txtPrice.text = it.balance.toString()
                    txtMoneyType.text = binding.root.context.getString(R.string.cash)
                    imgCardLogo.loadImg(it.cardLogo)

                }
            }
        }

    }

    override fun listeners() {
        binding.itemFromAccountCard.root.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFromAccountFragment())
        }
        binding.itemToAccountCard.root.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToToAccountFragment())
        }

        binding.itemFromMoneyValue.etEnterMoney.doAfterTextChanged { text ->
            viewmodel.onEvent(HomeUiEvent.ChangeMoneyValueRight(text.toString().toDoubleOrNull()))

        }
    }
}