package com.example.tbcexercises.domain.repository

import com.example.tbcexercises.domain.util.Resource
import java.io.File

interface ImageRepository {
    suspend fun uploadImage(file: File): Resource<Unit>
}