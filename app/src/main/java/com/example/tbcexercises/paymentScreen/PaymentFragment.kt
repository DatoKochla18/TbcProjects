package com.example.tbcexercises.paymentScreen


import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.Model.Card
import com.example.tbcexercises.databinding.FragmentPaymentBinding
import com.example.tbcexercises.extensions.getCard
import com.example.tbcexercises.utils.BaseFragment


class PaymentFragment : BaseFragment<FragmentPaymentBinding>(FragmentPaymentBinding::inflate) {
    private val viewModel: PaymentViewModel by viewModels()

    private val cardAdapter by lazy {
        CardViewPagerAdapter(onLongClick = { navigateToRemoveScreen(it) })
    }


    override fun start() {
        binding.pager.adapter = cardAdapter

        cardAdapter.submitList(viewModel.data.toList())
    }

    override fun listeners() {
        binding.txtAddNewCard.setOnClickListener {
            findNavController().navigate(
                PaymentFragmentDirections.actionPaymentFragmentToAddNewCardFragment()
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener("addCard", this) { _, bundle ->
            val card = bundle.getCard("newCard")!!

            viewModel.addCard(card)
            cardAdapter.submitList(viewModel.data.toList())

        }

        parentFragmentManager.setFragmentResultListener("removeCard", this) { _, bundle ->
            val uuid = bundle.getString("oldCardId")!!

            viewModel.removeCard(uuid)

            cardAdapter.submitList(viewModel.data.toList())
        }
    }

    private fun navigateToRemoveScreen(card: Card): Boolean {
        findNavController().navigate(
            PaymentFragmentDirections.actionPaymentFragmentToRemoveCardFragment(card)
        )
        return true
    }


}