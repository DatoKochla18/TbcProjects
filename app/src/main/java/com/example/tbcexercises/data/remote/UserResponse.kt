package com.example.tbcexercises.data.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val avatar: String? = null,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    val about: String,
    @SerialName("activation_status")
    val activationStatus: Float
) {
    val activationEnum = when {
        activationStatus <= 0 -> ActivationStatus.NOT_ACTIVATED
        activationStatus == 1f -> ActivationStatus.ACTIVE
        activationStatus == 2f -> ActivationStatus.USER_LEFT_EARLY
        activationStatus in 3f..22f -> ActivationStatus.USER_LEFT_HOURS_AGO
        else -> ActivationStatus.USER_NOT_ACTIVE
    }
}
