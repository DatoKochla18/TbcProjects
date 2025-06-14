package com.example.tbcexercises.domain.use_case

import com.example.tbcexercises.domain.model.GetBreed
import com.example.tbcexercises.domain.repository.BreedRepository
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError
import javax.inject.Inject

class SearchBreedUseCase @Inject constructor(
    private val breedRepository: BreedRepository,
) {

    suspend operator fun invoke(query: String): Resource<List<GetBreed>, NetworkError> {
        return breedRepository.searchBreeds(query)
    }
}