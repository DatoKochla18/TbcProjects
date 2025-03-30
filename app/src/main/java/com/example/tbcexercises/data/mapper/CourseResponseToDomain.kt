package com.example.tbcexercises.data.mapper

import com.example.tbcexercises.data.remote.response.CourseResponse
import com.example.tbcexercises.domain.model.GetCourse


fun CourseResponse.toDomain(): GetCourse = GetCourse(course = this.course)