package com.example.tbcexercises.presentation.util

sealed interface CardFindType {
    data class AccountNumber(val text: String) : CardFindType
    data class PersonalNumber(val text: String) : CardFindType
    data class PhoneNumber(val text: String) : CardFindType
}