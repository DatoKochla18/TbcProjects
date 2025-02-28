package com.example.tbcexercises.domain.repository

import com.example.tbcexercises.domain.model.Location
import com.example.tbcexercises.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getLocations(): Flow<Resource<List<Location>>>
}