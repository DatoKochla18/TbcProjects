package com.example.tbcexercises.core.data.manager

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.tbcexercises.core.domain.manager.UserSessionManager
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