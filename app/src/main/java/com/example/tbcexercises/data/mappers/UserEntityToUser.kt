package com.example.tbcexercises.data.mappers

import com.example.tbcexercises.data.local.room.entity.UserEntity
import com.example.tbcexercises.domain.model.User

fun UserEntity.toUser(): User {
    return User(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        avatar = this.avatar
    )
}