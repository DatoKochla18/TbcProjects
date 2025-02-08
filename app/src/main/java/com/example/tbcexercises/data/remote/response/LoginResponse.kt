package com.example.tbcexercises.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(val token: String)

