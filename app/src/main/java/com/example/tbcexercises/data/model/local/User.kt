package com.example.tbcexercises.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tbcexercises.data.model.remote.ActivationStatus

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val avatar: String?,
    val firstName: String,
    val lastName: String,
    val about: String,
    val activationStatus: ActivationStatus
)
