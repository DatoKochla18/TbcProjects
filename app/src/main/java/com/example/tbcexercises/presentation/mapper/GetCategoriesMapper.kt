package com.example.tbcexercises.presentation.mapper

import com.example.tbcexercises.domain.model.GetCategories
import com.example.tbcexercises.presentation.model.Category


fun GetCategories.toPresentation(): Category = Category(
    id = this.id,
    name = this.name,
    children = this.children.map { it.toPresentation() },
    depth = this.depth
)
