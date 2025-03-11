package com.example.tbcexercises.core.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(val email: String, val password: String)