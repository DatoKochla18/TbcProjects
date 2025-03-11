package com.example.tbcexercises.core.domain.util

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: ErrorTypes? = null
)