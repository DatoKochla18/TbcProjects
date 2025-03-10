package com.example.tbcexercises.data.mappers

import com.example.tbcexercises.data.remote.response.RegisterResponse
import com.example.tbcexercises.domain.model.User

fun RegisterResponse.toUser(): User = User(token = this.token)