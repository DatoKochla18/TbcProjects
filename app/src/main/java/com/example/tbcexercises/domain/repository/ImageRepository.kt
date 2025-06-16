package com.example.tbcexercises.domain.repository

import com.example.tbcexercises.domain.model.GetImage
import com.example.tbcexercises.domain.model.GetImageDetail
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError

interface ImageRepository {

    suspend fun getImages(): Resource<List<GetImage>, NetworkError>

    suspend fun searchImages(keyword: String): Resource<List<GetImage>, NetworkError>

    suspend fun getImage(hex: String): Resource<List<GetImageDetail>, NetworkError>

}