package com.example.tbcexercises.data.repository

import com.example.tbcexercises.data.mappers.toLocation
import com.example.tbcexercises.data.remote.service.LocationService
import com.example.tbcexercises.domain.model.Location
import com.example.tbcexercises.domain.repository.LocationRepository
import com.example.tbcexercises.utils.Resource
import com.example.tbcexercises.utils.handleNetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationService: LocationService
) : LocationRepository {
    override fun getLocations(): Flow<Resource<List<Location>>> {
        return handleNetworkRequest { locationService.getLocations() }.map { response ->
            when (response) {
                is Resource.Error -> Resource.Error(response.message)
                Resource.Loading -> Resource.Loading
                is Resource.Success -> Resource.Success(response.data.map { it.toLocation() })
            }
        }
    }
}