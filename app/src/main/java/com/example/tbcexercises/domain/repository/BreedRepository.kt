package com.example.tbcexercises.domain.repository

import com.example.tbcexercises.domain.model.GetBreed
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError

interface BreedRepository {

    suspend fun getBreeds(): Resource<List<GetBreed>, NetworkError>


    suspend fun searchBreeds(query: String): Resource<List<GetBreed>, NetworkError>
}