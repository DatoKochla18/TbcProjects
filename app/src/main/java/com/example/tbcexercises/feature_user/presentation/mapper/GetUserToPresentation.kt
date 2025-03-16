package com.example.tbcexercises.feature_user.presentation.mapper

import com.example.tbcexercises.feature_user.domain.model.GetUser
import com.example.tbcexercises.feature_user.presentation.model.User

fun GetUser.toPresentation(): User = User(id, email, fullName, avatar)