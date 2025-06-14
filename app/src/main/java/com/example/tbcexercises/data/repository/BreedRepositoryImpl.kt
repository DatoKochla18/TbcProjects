package com.example.tbcexercises.data.repository

import com.example.tbcexercises.data.mapper.toDomain
import com.example.tbcexercises.data.remote.service.BreedService
import com.example.tbcexercises.data.remote.utils.ApiHelper
import com.example.tbcexercises.domain.model.GetBreed
import com.example.tbcexercises.domain.repository.BreedRepository
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError
import com.example.tbcexercises.domain.util.mapData
import javax.inject.Inject

class BreedRepositoryImpl @Inject constructor(
    private val breedService: BreedService,
    private val apiHelper: ApiHelper,
) : BreedRepository {
    override suspend fun getBreeds(): Resource<List<GetBreed>, NetworkError> {
        return apiHelper.handleHttpRequest { breedService.getBreeds() }
            .mapData { it.map { it.toDomain() } }
    }

    override suspend fun searchBreeds(query: String): Resource<List<GetBreed>, NetworkError> {
        return apiHelper.handleHttpRequest { breedService.searchBreeds(query) }
            .mapData { it.map { it.toDomain() } }
    }

}