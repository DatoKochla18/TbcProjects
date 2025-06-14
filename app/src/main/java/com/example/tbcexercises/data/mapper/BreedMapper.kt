package com.example.tbcexercises.data.mapper

import com.example.tbcexercises.data.remote.response.BreedResponse
import com.example.tbcexercises.domain.model.GetBreed


fun BreedResponse.toDomain(): GetBreed = GetBreed(
    name, breedOrigin?:"", image?.url?:""
)