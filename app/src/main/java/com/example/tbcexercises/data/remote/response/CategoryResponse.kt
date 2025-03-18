package com.example.tbcexercises.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(val id: String, val name: String, val children: List<CategoryResponse>)