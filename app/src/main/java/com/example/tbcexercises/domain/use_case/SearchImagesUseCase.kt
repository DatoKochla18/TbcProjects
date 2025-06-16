package com.example.tbcexercises.domain.use_case

import com.example.tbcexercises.domain.model.GetImage
import com.example.tbcexercises.domain.repository.ImageRepository
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError
import com.example.tbcexercises.domain.util.map
import javax.inject.Inject

class SearchImagesUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
) {

    suspend operator fun invoke(keyword: String): Resource<List<GetImage>, NetworkError> {
        return imageRepository.searchImages(keyword).map {
            it.groupBy { it.userName }
                .map { (_, images) -> images.maxByOrNull { it.id } }
                .filterNotNull()
        }
    }
}