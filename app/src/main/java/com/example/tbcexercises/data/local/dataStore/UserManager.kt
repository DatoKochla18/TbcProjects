package com.example.tbcexercises.data.local.dataStore

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.tbcexercises.App
import com.example.tbcexercises.data.local.dataStore.PreferencesKey.EMAIL_KEY
import com.example.tbcexercises.data.local.dataStore.PreferencesKey.REMEMBER_ME_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user-session")


object UserManager {
    suspend fun setSession(rememberMe: Boolean, email: String) {
        App.CONTEXT!!.dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = email
            preferences[REMEMBER_ME_KEY] = rememberMe
        }
    }
    val emailFlow: Flow<String?> = App.CONTEXT!!.dataStore.data.map { preferences ->
        preferences[EMAIL_KEY]
    }

    val rememberMeFlow: Flow<Boolean> = App.CONTEXT!!.dataStore.data.map { preferences ->
        preferences[REMEMBER_ME_KEY] ?: false
    }
}