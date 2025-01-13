package com.example.tbcexercises.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Card(
    val id: String = UUID.randomUUID().toString(),
    val cardNumber: Long,
    val cardHolderName: String,
    val expiredDate:String,
    val ccv:Int,
    val cardType:CardType
) : Parcelable {
}