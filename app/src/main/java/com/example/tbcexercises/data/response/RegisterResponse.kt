package com.example.tbcexercises.data.response

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(val id: Int, val token: String)