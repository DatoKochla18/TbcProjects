package com.example.tbcexercises.addNewCardScreen


import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.model.Card
import com.example.tbcexercises.model.CardType
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentAddNewCardBinding
import com.example.tbcexercises.extensions.toCardNumber
import com.example.tbcexercises.utils.BaseFragment
import com.example.tbcexercises.utils.Result


class AddNewCardFragment :
    BaseFragment<FragmentAddNewCardBinding>(FragmentAddNewCardBinding::inflate) {

    private val viewModel: AddNewCardViewModel by viewModels()
    override fun start() {

    }

    override fun listeners() {
        onChangedListeners()

        binding.btnAddCard.setOnClickListener {
            addCard()
        }
        binding.btnArrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun onChangedListeners() {
        binding.apply {
            etCardHolder.doAfterTextChanged {
                binding.txtCardHolderNameDisplay.text = binding.etCardHolder.text
            }
            etCardExpiresDate.doAfterTextChanged {
                binding.txtCardDateDisplay.text = binding.etCardExpiresDate.text
            }
            etCardNumber.doAfterTextChanged {
                binding.txtCardNumberDisplay.text =
                    binding.etCardNumber.text.toString().toLongOrNull()?.toCardNumber() ?: ""
            }
            rbGroups.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbMasterCard -> binding.imgCard.setImageResource(R.drawable.master_card)
                    else -> binding.imgCard.setImageResource(R.drawable.visa_card)
                }
            }

        }
    }

    private fun addCard() {
        val cardHolderName = binding.etCardHolder.text.toString()
        val cardNumber = binding.etCardNumber.text.toString()
        val cardCCV = binding.etCardCCV.text.toString()
        val cardExpireDate = binding.etCardExpiresDate.text.toString()
        val cardType = when (binding.rbGroups.checkedRadioButtonId) {
            R.id.rbMasterCard -> CardType.MASTER_CARD
            else -> CardType.VISA
        }

        val result = viewModel.checkCard(
            holderName = cardHolderName,
            cardNumber = cardNumber,
            cardCCV = cardCCV,
            cardExpireDate
        )
        when (result) {
            is Result.OnSuccess -> {
                onAddCardSuccess(
                    cardHolderName = cardHolderName,
                    cardNumber = cardNumber,
                    cardCCV = cardCCV,
                    cardExpireDate = cardExpireDate,
                    cardType = cardType
                )
            }

            is Result.OnError -> {
                Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onAddCardSuccess(
        cardHolderName: String,
        cardNumber: String,
        cardCCV: String,
        cardExpireDate: String,
        cardType: CardType
    ) {
        val card = Card(
            cardNumber = cardNumber.toLong(),
            ccv = cardCCV.toInt(),
            cardHolderName = cardHolderName,
            expiredDate = cardExpireDate,
            cardType = cardType
        )
        parentFragmentManager.setFragmentResult("addCard", bundleOf("newCard" to card))
        findNavController().popBackStack()

    }


}