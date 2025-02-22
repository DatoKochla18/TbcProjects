package com.example.tbcexercises.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class StoryResponse(
    val id : Int,
    val cover : String,
    val title: String
)

