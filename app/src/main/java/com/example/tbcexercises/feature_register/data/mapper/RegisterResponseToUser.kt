package com.example.tbcexercises.feature_register.data.mapper

import com.example.tbcexercises.core.domain.model.Profile
import com.example.tbcexercises.feature_register.data.remote.response.RegisterResponse

fun RegisterResponse.toProfile(): Profile = Profile(token = this.token)