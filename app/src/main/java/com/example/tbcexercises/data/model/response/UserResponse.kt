package com.example.tbcexercises.data.model.response

import com.example.tbcexercises.data.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val data: List<User>
)