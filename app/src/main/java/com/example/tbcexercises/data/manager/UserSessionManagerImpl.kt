package com.example.tbcexercises.data.manager

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.tbcexercises.domain.manager.UserSessionManager
import com.example.tbcexercises.utils.Constants.EMAIL_KEY
import com.example.tbcexercises.utils.Constants.REMEMBER_ME_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSessionManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserSessionManager {

    override suspend fun <T> saveValue(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
            Log.d("saved", preferences[key].toString())
        }
    }

    override fun <T> readValue(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return dataStore.data.map { preferences ->
            preferences[key] ?: defaultValue
        }
    }
}