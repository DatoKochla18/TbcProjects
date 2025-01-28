package com.example.tbcexercises.data.model.response

import com.example.tbcexercises.data.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val page: Int,
    @SerialName("per_page")
    val perPage: Int,
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    val data : List<User>
)