package com.example.tbcexercises.domain.use_case

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.tbcexercises.domain.model.GetImageDetail
import com.example.tbcexercises.domain.repository.ImageRepository
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError
import com.example.tbcexercises.domain.util.mapList
import javax.inject.Inject

class GetImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
    private val dateTimeFormatter: DateTimeFormatterUseCase,
) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(hex: String): Resource<List<GetImageDetail>, NetworkError> {
        return imageRepository.getImage(hex)
            .mapList { it.copy(dateCreated = dateTimeFormatter(it.dateCreated)) }
    }
}