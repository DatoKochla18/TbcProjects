package com.example.tbcexercises.data.mappers

import com.example.tbcexercises.data.remote.response.LoginResponse
import com.example.tbcexercises.domain.model.User

fun LoginResponse.toUser(): User = User(token = this.token)