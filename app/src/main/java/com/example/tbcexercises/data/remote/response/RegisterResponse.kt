package com.example.tbcexercises.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(val id: Int, val token: String)