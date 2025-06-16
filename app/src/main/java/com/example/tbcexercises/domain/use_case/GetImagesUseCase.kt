package com.example.tbcexercises.domain.use_case

import com.example.tbcexercises.domain.model.GetImage
import com.example.tbcexercises.domain.repository.ImageRepository
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError
import javax.inject.Inject

class GetImagesUseCase@Inject constructor(
    private val imageRepository: ImageRepository
) {

    operator suspend fun invoke():Resource<List<GetImage>,NetworkError>{
        return imageRepository.getImages()
    }
}