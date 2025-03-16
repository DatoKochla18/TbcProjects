package com.example.tbcexercises.feature_user.data.mapper

import com.example.tbcexercises.feature_user.data.local.entity.UserEntity
import com.example.tbcexercises.feature_user.data.remote.response.UserResponse

fun UserResponse.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        avatar = this.avatar
    )
}
