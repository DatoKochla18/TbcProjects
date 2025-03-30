package com.example.tbcexercises.data.repository

import com.example.tbcexercises.data.mapper.toDomain
import com.example.tbcexercises.data.remote.service.CourseService
import com.example.tbcexercises.data.remote.util.ApiHelper
import com.example.tbcexercises.data.remote.util.mapData
import com.example.tbcexercises.domain.model.GetCourse
import com.example.tbcexercises.domain.repository.CourseRepository
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val courseService: CourseService,
) : CourseRepository {
    override suspend fun getCourse(): Resource<GetCourse, NetworkError> {
        return apiHelper.handleNetworkRequestAsSuspend { courseService.getCourse() }.mapData {
            it.toDomain()
        }
    }

}