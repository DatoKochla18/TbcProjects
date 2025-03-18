package com.example.tbcexercises.domain.repository

import com.example.tbcexercises.domain.model.GetCategories
import com.example.tbcexercises.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategories(): Flow<Resource<List<GetCategories>>>
}