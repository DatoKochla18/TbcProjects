package com.example.tbcexercises.presentation.mapper

import com.example.tbcexercises.domain.model.GetBreed
import com.example.tbcexercises.presentation.model.Breed

fun GetBreed.toPresentation(): Breed = Breed(name, breedOrigin, imageUrl)