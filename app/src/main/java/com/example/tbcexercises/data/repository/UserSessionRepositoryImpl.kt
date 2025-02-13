package com.example.tbcexercises.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.tbcexercises.domain.repository.UserSessionRepository
import com.example.tbcexercises.utils.Constants.EMAIL_KEY
import com.example.tbcexercises.utils.Constants.REMEMBER_ME_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSessionRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    UserSessionRepository {
    override suspend fun setSession(rememberMe: Boolean, email: String) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = email
            preferences[REMEMBER_ME_KEY] = rememberMe
        }
    }

    override val emailFlow: Flow<String?> = dataStore.data.map { preferences ->
        preferences[EMAIL_KEY]
    }

    override val rememberMeFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[REMEMBER_ME_KEY] ?: false
    }
}