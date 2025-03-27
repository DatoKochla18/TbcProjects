package com.example.tbcexercises.domain.use_case

import com.example.tbcexercises.domain.repository.ImageRepository
import com.example.tbcexercises.domain.util.Resource
import java.io.File
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(private val imageRepository: ImageRepository) {
    suspend operator fun invoke(file: File): Resource<Unit> {
        return imageRepository.uploadImage(file)
    }
}