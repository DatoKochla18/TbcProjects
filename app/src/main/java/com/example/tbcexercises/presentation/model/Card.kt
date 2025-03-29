package com.example.tbcexercises.presentation.model

data class Card(
    val id: Int,
    val accountName: String,
    val accountNumber: String,
    val valueType: String,
    val balance: Int,
    val cardLogo: String?,
)