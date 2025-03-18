package com.example.tbcexercises.domain.repository

import com.example.tbcexercises.domain.model.GetCategories
import com.example.tbcexercises.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategories(): Flow<Resource<List<GetCategories>>>
}