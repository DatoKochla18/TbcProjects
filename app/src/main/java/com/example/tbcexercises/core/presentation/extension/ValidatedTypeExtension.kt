package com.example.tbcexercises.core.presentation.extension

import com.example.tbcexercises.core.presentation.util.ValidatedType


fun ValidatedType.asBoolean(): Boolean {
    return (this == ValidatedType.CORRECT)
}