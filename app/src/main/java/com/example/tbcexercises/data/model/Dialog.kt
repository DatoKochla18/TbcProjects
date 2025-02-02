package com.example.tbcexercises.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dialog(@PrimaryKey val id: String, val text: String) {
}