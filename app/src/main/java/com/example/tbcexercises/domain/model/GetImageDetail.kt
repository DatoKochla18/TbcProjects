package com.example.tbcexercises.domain.model


data class GetImageDetail(
    val id:Int,
    val title:String,
    val userName:String,
    val dateCreated:String,
    val rgb: GetRGB,
    val description:String,
    val badgeUrl:String
)

