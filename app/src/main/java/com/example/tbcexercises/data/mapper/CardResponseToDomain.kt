package com.example.tbcexercises.data.mapper

import com.example.tbcexercises.data.remote.response.CardResponse
import com.example.tbcexercises.domain.model.GetCard


fun CardResponse.toDomain(): GetCard = GetCard(
    id = id,
    accountName = accountName,
    accountNumber = accountNumber,
    balance = balance,
    cardLogo = cardLogo,
    valueType = valueType
)