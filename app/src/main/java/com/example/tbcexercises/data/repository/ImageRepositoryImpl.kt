package com.example.tbcexercises.data.repository

import android.net.Uri
import com.example.tbcexercises.domain.repository.ImageRepository
import com.example.tbcexercises.domain.util.Resource
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
) : ImageRepository {
    override suspend fun uploadImage(file: File): Resource<Unit> {
        return try {
            val fileUri = Uri.fromFile(file)
            val storageRef = firebaseStorage.reference.child("images/${file.name}")
            storageRef.putFile(fileUri).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.toString())
        }
    }
}