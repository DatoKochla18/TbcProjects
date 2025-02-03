package com.example.tbcexercises.data.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class UserListResponse(val users: List<UserResponse>) {
}