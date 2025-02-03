package com.example.tbcexercises.data.model.remote

enum class ActivationStatus(val text: String) {
    NOT_ACTIVATED("User is not Active"),
    ACTIVE("Online"),
    USER_LEFT_EARLY("User was active few minutes ago"),
    USER_LEFT_HOURS_AGO("User was active few hours ago"),
    USER_NOT_ACTIVE("User was active long time ago")
}