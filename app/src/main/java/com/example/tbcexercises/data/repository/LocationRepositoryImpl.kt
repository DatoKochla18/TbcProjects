package com.example.tbcexercises.data.repository

import com.example.tbcexercises.data.mappers.toLocation
import com.example.tbcexercises.data.remote.apis.LocationApi
import com.example.tbcexercises.domain.model.Location
import com.example.tbcexercises.domain.repository.LocationRepository
import com.example.tbcexercises.utils.helpers.Resource
import com.example.tbcexercises.utils.helpers.handleNetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(private val locationApi: LocationApi) :
    LocationRepository {
    override fun getUsers(): Flow<Resource<List<Location>>> {
        return handleNetworkRequest { locationApi.getUser() }
            .map { resource ->
                when (resource) {
                    is Resource.Success -> Resource.Success(resource.data.map { it.toLocation() })
                    is Resource.Error -> Resource.Error(resource.message)
                    is Resource.Loading -> Resource.Loading
                }
            }
    }
}