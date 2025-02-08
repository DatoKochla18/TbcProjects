package com.example.tbcexercises.utils.exntension

import android.content.Context
import androidx.datastore.dataStore
import com.example.tbcexercises.data.local.dataStore.UserSessionSerializer

val Context.dataStore by dataStore("user-session.json", UserSessionSerializer)
