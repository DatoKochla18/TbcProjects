package com.example.tbcexercises.presentation.mapper

import com.example.tbcexercises.domain.model.GetCard
import com.example.tbcexercises.presentation.model.Card


fun GetCard.toPresentation(): Card = Card(
    id = id,
    accountName = accountName,
    accountNumber = accountNumber,
    balance = balance,
    cardLogo = cardLogo,
    valueType = valueType
)