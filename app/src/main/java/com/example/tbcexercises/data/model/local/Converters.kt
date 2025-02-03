package com.example.tbcexercises.data.model.local

import androidx.room.TypeConverter
import com.example.tbcexercises.data.model.remote.ActivationStatus

class Converters {
    @TypeConverter
    fun fromInt(value: Int): ActivationStatus {
        return ActivationStatus.entries[value]
    }

    @TypeConverter
    fun enumToInt(activationStatus: ActivationStatus): Int {
        return activationStatus.ordinal
    }
}