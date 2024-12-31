package com.example.tbcexercises.Models

import java.util.UUID

data class Message(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val dateTime: Long = System.currentTimeMillis()
)
