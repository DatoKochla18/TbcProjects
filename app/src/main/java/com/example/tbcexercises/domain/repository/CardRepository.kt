package com.example.tbcexercises.domain.repository

import com.example.tbcexercises.domain.model.GetCard
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError

interface CardRepository {
    suspend fun getCards(): Resource<List<GetCard>, NetworkError>
}