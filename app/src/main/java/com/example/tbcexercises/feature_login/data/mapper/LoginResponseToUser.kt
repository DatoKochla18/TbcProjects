package com.example.tbcexercises.feature_login.data.mapper

import com.example.tbcexercises.core.domain.model.Profile
import com.example.tbcexercises.feature_login.data.remote.response.LoginResponse

fun LoginResponse.toProfile(): Profile = Profile(token = this.token)