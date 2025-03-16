package com.example.tbcexercises.feature_user.domain.model


data class GetUser(
    val id: Int,
    val email: String,
    val fullName:String,
    val avatar: String
)