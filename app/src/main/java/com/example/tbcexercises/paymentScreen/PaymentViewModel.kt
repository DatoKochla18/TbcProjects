package com.example.tbcexercises.paymentScreen

import androidx.lifecycle.ViewModel
import com.example.tbcexercises.model.Card
import com.example.tbcexercises.model.CardType

class PaymentViewModel : ViewModel() {
    val data = mutableListOf(
        Card(
            cardNumber = 1234123456321234,
            ccv = 3343,
            expiredDate = "03/27",
            cardHolderName = "Davit Kotchla",
            cardType = CardType.VISA
        ),
        Card(
            cardNumber = 1245312234561234,
            ccv = 3133,
            expiredDate = "03/27",
            cardHolderName = "Gio Kotchla",
            cardType = CardType.MASTER_CARD
        ),
        Card(
            cardNumber = 1234132456321234,
            ccv = 333,
            expiredDate = "03/27",
            cardHolderName = "Elene Kotchla",
            cardType = CardType.VISA
        )

    )

    fun addCard(card: Card) {
        data.add(card)
    }

    fun removeCard(uuid: String) {
        data.removeIf { card -> card.id == uuid }
    }

}