package com.example.tbcexercises.data.local.dataStore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKey {
     val EMAIL_KEY = stringPreferencesKey("user_email")
     val REMEMBER_ME_KEY = booleanPreferencesKey("remember_me")
}