package com.example.tbcexercises.domain.use_case

import com.example.tbcexercises.domain.model.GetCourse
import com.example.tbcexercises.domain.repository.CourseRepository
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError
import javax.inject.Inject

class GetCourseUseCase @Inject constructor(
    private val courseRepository: CourseRepository,
) {
    suspend operator fun invoke(): Resource<GetCourse, NetworkError> {
        return courseRepository.getCourse()
    }
}