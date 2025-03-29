package com.example.tbcexercises.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CardResponse(
    val id: Int,
    @SerialName("account_name")
    val accountName: String,
    @SerialName("account_number")
    val accountNumber: String,
    @SerialName("valute_type")
    val valueType: String,
    val balance: Int,
    @SerialName("card_logo")
    val cardLogo: String?,
)
