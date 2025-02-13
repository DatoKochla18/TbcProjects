package com.example.tbcexercises.data.mappers

import com.example.tbcexercises.data.local.room.entity.UserEntity
import com.example.tbcexercises.data.remote.response.UserResponse

fun UserResponse.toUserEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        avatar = this.avatar
    )
}
