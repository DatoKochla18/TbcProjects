package com.example.tbcexercises.data.mapper

import com.example.tbcexercises.data.remote.response.CategoryResponse
import com.example.tbcexercises.domain.model.GetCategories

fun CategoryResponse.toDomain(currentDepth: Int = 0): GetCategories {
    return GetCategories(
        id = id,
        name = name,
        children = children.map { it.toDomain(currentDepth + 1) },
        depth = currentDepth
    )
}