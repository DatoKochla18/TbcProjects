package com.example.tbcexercises.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse(
    val lat: Double,
    val lan: Double,
    val title: String,
    val address: String
)
