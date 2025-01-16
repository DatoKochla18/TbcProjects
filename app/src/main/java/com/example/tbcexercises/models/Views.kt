package com.example.tbcexercises.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Views(
    @SerialName("field_id")
    val fieldId: Int,
    val hint: String,
    @SerialName("field_type")
    val filedType: String,
    val keyboard: String? = null,
    val required: Boolean,
    @SerialName("is_active")
    val isActive: Boolean,
    val icon: String,

    //hand added
    var userInput: String? = null,
) {
}
