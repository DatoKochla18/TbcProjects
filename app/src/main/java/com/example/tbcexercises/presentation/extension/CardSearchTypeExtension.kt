package com.example.tbcexercises.presentation.extension

import com.example.tbcexercises.domain.util.CardSearchType
import com.example.tbcexercises.presentation.util.CardFindType


fun CardFindType.toDomain(): CardSearchType {
    return when (this) {
        is CardFindType.AccountNumber -> CardSearchType.AccountNumber(this.text)
        is CardFindType.PersonalNumber -> CardSearchType.PersonalNumber(this.text)
        is CardFindType.PhoneNumber -> CardSearchType.PhoneNumber(this.text)
    }
}