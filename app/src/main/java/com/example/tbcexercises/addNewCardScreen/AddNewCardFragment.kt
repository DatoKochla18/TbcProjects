package com.example.tbcexercises.addNewCardScreen


import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.model.Card
import com.example.tbcexercises.model.CardType
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentAddNewCardBinding
import com.example.tbcexercises.extensions.toCardNumber
import com.example.tbcexercises.utils.BaseFragment


class AddNewCardFragment :
    BaseFragment<FragmentAddNewCardBinding>(FragmentAddNewCardBinding::inflate) {
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
        val cardholderName = binding.etCardHolder.text.toString()
        val cardNumber = binding.etCardNumber.text.toString().toLong()
        val cardCCV = binding.etCardCCV.text.toString().toInt()
        val cardExpireDate = binding.etCardExpiresDate.text.toString()
        val cardType = when (binding.rbGroups.checkedRadioButtonId) {
            R.id.rbMasterCard -> CardType.MASTER_CARD
            else -> CardType.VISA
        }

        val card = Card(
            cardNumber = cardNumber,
            ccv = cardCCV,
            cardHolderName = cardholderName,
            expiredDate = cardExpireDate,
            cardType = cardType
        )

        parentFragmentManager.setFragmentResult("addCard", bundleOf("newCard" to card))
        findNavController().popBackStack()

    }


}