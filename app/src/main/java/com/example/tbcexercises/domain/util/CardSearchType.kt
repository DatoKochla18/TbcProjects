package com.example.tbcexercises.domain.util

sealed interface CardSearchType {
    data object None : CardSearchType
    data class AccountNumber(val text: String) : CardSearchType
    data class PersonalNumber(val text: String) : CardSearchType
    data class PhoneNumber(val text: String) : CardSearchType
}