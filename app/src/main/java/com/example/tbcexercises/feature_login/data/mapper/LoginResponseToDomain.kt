package com.example.tbcexercises.feature_login.data.mapper

import com.example.tbcexercises.feature_login.data.remote.response.LoginResponse
import com.example.tbcexercises.feature_login.domain.model.GetProfileLogin

fun LoginResponse.toDomain(): GetProfileLogin = GetProfileLogin(token = this.token)