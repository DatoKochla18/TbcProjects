package com.example.tbcexercises.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UserListResponse(
    val page: Int,
    @SerialName("per_page")
    val perPage: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    val data: List<UserResponse>
)