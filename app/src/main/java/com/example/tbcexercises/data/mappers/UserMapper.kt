package com.example.tbcexercises.data.mappers

import com.example.tbcexercises.data.local.entity.UserEntity
import com.example.tbcexercises.data.remote.response.UserResponse
import com.example.tbcexercises.domain.model.User

fun UserResponse.toUserEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        avatar = this.avatar
    )
}


fun UserEntity.toUser(): User {
    return User(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        avatar = this.avatar
    )
}