package com.example.tbcexercises.addNewCardScreen

import androidx.lifecycle.ViewModel
import com.example.tbcexercises.utils.Result

class AddNewCardViewModel : ViewModel() {

    fun checkCard(
        holderName: String,
        cardNumber: String,
        cardCCV: String,
        cardExpireDate: String
    ): Result {
        if (holderName.isEmpty() || cardNumber.isEmpty() || cardCCV.isEmpty() || cardExpireDate.isEmpty()) {
            return Result.OnError("Field Should not be empty")
        }
        if (!(holderName.all { it.isLetter() || it == ' ' })) {
            return Result.OnError("Name Should Contain only letters")
        }

        if (cardNumber.toLongOrNull() == null || cardNumber.length != 16) {
            return Result.OnError("Card Number should have 16 digits")
        }

        if (cardCCV.toIntOrNull() == null || cardCCV.length != 3) {
            return Result.OnError("Card CCV should have 3 digits")
        }
        if (cardExpireDate.length != 5 || cardExpireDate[2] != '/') {
            return Result.OnError("Expiry Date should be in format MM/YY")
        }


        if (!cardExpireDate.substring(0, 2).all { it.isDigit() } || !cardExpireDate.substring(3, 5)
                .all { it.isDigit() }) {
            return Result.OnError("Expiry Date should be in format MM/YY")
        }
        return Result.OnSuccess()
    }

}