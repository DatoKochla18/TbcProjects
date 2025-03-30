package com.example.tbcexercises.domain.repository

import com.example.tbcexercises.domain.model.GetCourse
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError

interface CourseRepository {

    suspend fun getCourse():Resource<GetCourse,NetworkError>
}