package com.example.tbcexercises.domain.repository

import com.example.tbcexercises.domain.model.Location
import com.example.tbcexercises.utils.helpers.Resource
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getUsers(): Flow<Resource<List<Location>>>
}