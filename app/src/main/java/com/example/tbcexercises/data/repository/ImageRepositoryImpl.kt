package com.example.tbcexercises.data.repository

import com.example.tbcexercises.data.mapper.toDomain
import com.example.tbcexercises.data.remote.service.ImageService
import com.example.tbcexercises.data.remote.utils.ApiHelper
import com.example.tbcexercises.domain.model.GetImage
import com.example.tbcexercises.domain.model.GetImageDetail
import com.example.tbcexercises.domain.repository.ImageRepository
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError
import com.example.tbcexercises.domain.util.mapList
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageService: ImageService, private val apiHelper: ApiHelper,
) : ImageRepository {
    override suspend fun getImages(): Resource<List<GetImage>, NetworkError> {
        return apiHelper.handleHttpRequest {
            imageService.getImages()
        }.mapList { it.toDomain() }
    }

    override suspend fun searchImages(keyword: String): Resource<List<GetImage>, NetworkError> {
        return apiHelper.handleHttpRequest { imageService.searchImages(keyword) }
            .mapList { it.toDomain() }
    }

    override suspend fun getImage(hex: String): Resource<List<GetImageDetail>, NetworkError> {
        return apiHelper.handleHttpRequest { imageService.getImage(hex = hex) }
            .mapList { it.toDomain() }
    }

}