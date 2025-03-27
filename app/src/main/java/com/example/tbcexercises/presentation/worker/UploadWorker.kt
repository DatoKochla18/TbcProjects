package com.example.tbcexercises.presentation.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tbcexercises.domain.use_case.UploadImageUseCase
import com.example.tbcexercises.domain.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.File

@HiltWorker
class UploadWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
     private val uploadImageUseCase: UploadImageUseCase,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val filePath = inputData.getString("file_path") ?: return Result.failure()
        val file = File(filePath)
        return when (uploadImageUseCase(file)) {
            is Resource.Error -> Result.failure()
            is Resource.Success -> Result.success()
        }
    }
}