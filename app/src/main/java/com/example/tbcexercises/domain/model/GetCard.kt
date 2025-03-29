package com.example.tbcexercises.domain.model


data class GetCard(
    val id: Int,
    val accountName: String,
    val accountNumber: String,
    val valueType: String,
    val balance: Int,
    val cardLogo: String?,
)