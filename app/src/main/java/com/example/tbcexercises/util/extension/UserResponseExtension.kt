package com.example.tbcexercises.util.extension

import com.example.tbcexercises.data.local.User
import com.example.tbcexercises.data.remote.UserResponse

fun UserResponse.convertUserResponseToUserEntity(): User {
    return User(
        id = this.id,
        about = this.about,
        activationStatus = this.activationEnum,
        avatar = this.avatar,
        firstName = this.firstName,
        lastName = this.lastName
    )
}