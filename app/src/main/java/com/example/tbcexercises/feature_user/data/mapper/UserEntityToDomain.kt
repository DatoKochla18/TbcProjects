package com.example.tbcexercises.feature_user.data.mapper

import com.example.tbcexercises.feature_user.domain.model.GetUser
import com.example.tbcexercises.feature_user.data.local.entity.UserEntity


fun UserEntity.toDomain(): GetUser {
    return GetUser(
        id = this.id,
        email = this.email,
        fullName = this.firstName + " " + this.lastName,
        avatar = this.avatar
    )
}