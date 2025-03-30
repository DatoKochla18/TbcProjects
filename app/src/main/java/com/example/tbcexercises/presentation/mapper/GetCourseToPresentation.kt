package com.example.tbcexercises.presentation.mapper

import com.example.tbcexercises.domain.model.GetCourse
import com.example.tbcexercises.presentation.model.Course


fun GetCourse.toPresentation(): Course = Course(course = this.course)