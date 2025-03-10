package com.example.tbcexercises.data.mappers

import com.example.tbcexercises.data.local.room.entity.UserEntity
import com.example.tbcexercises.domain.model.Profile

fun UserEntity.toProfile(): Profile {
    return Profile(
        id = this.id,
        email = this.email,
        fullName = this.firstName + " " + this.lastName,
        avatar = this.avatar
    )
}