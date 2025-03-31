package com.example.tbcexercises.presentation.mapper

import com.example.tbcexercises.domain.model.GetCard
import com.example.tbcexercises.presentation.model.Card


fun GetCard.toPresentation(): Card = Card(
    id = id,
    accountName = accountName,
    accountNumber = if (accountNumber.length > 8)
        "*".repeat(accountNumber.length - 8) + accountNumber.takeLast(8)
    else accountNumber,
    balance = balance,
    cardLogo = cardLogo,
    valueType = valueType,
    valueTypeFormatted = if (this.valueType.lowercase() == "eur") "€" else if (this.valueType.lowercase() == "usd") "$" else "₾"
)