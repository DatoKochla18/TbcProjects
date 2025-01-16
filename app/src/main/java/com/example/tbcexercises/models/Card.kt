package com.example.tbcexercises.models

import kotlinx.serialization.Serializable

@Serializable
data class Card(val views: List<Views>) {
}