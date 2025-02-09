package com.example.tbcexercises.data.local.dataStore

import android.content.Context
import androidx.datastore.dataStore
import com.example.tbcexercises.App

val Context.dataStore by dataStore("user-session.json", UserSessionSerializer)


object UserManager {
    suspend fun setSession(rememberMe: Boolean, email: String) {
        App.CONTEXT!!.dataStore.updateData {
            it.copy(rememberMe = rememberMe, email = email)
        }
    }
}