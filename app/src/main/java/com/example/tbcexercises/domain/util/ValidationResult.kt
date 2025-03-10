package com.example.tbcexercises.domain.util

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)