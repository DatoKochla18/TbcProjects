package com.example.tbcexercises.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    val id:Int,
    val title:String,
    val userName:String,
    val hex:String,
    val badgeUrl:String
)

