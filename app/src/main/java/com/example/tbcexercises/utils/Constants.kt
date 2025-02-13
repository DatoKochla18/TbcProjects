package com.example.tbcexercises.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val BASE_URL = "https://reqres.in/api/"

    val EMAIL_KEY = stringPreferencesKey("user_email")
    val REMEMBER_ME_KEY = booleanPreferencesKey("remember_me")
}