package com.example.tbcexercises.data.request

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(val email: String, val password: String)
