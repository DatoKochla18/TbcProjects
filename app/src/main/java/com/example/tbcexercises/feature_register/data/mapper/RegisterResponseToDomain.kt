package com.example.tbcexercises.feature_register.data.mapper

import com.example.tbcexercises.feature_register.data.remote.response.RegisterResponse
import com.example.tbcexercises.feature_register.domain.model.GetProfileRegistered

fun RegisterResponse.toDomain(): GetProfileRegistered =
    GetProfileRegistered(id = this.id, token = this.token)