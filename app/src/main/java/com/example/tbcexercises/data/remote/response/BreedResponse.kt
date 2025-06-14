package com.example.tbcexercises.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreedResponse(

    val name: String,
    @SerialName("origin")
    val breedOrigin: String? = "",
    @SerialName("image")
    val image:ImageResponse? = null
    )

@Serializable
data class ImageResponse(val url:String?)
