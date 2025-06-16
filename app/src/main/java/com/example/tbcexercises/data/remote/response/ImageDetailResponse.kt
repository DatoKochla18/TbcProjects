package com.example.tbcexercises.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ImageDetailResponse(
    val id:Int,
    val title:String,
    val userName:String,
    val dateCreated:String,
    val rgb: RGBResponse,
    val description:String,
    val badgeUrl:String
)

