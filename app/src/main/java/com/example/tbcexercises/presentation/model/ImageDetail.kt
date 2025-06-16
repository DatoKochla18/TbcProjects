package com.example.tbcexercises.presentation.model


data class ImageDetail(
    val id:Int,
    val title:String,
    val userName:String,
    val dateCreated:String,
    val rgb: RGB,
    val description:String,
    val badgeUrl:String
)

