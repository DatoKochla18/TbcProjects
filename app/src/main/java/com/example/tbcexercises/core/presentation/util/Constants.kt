package com.example.tbcexercises.core.presentation.util

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    val EMAIL_KEY = stringPreferencesKey("user_email")
    val REMEMBER_ME_KEY = booleanPreferencesKey("remember_me")
}