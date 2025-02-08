package com.example.tbcexercises.data.local.dataStore

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object UserSessionSerializer : Serializer<UserSession> {
    override val defaultValue: UserSession
        get() = UserSession()

    override suspend fun readFrom(input: InputStream): UserSession {
        return try {
            Json.decodeFromString(
                UserSession.serializer(), string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserSession, output: OutputStream) {
        //this kill of advantages of using DataStore over SharedPreferences but some bug annoyed me
        output.write(Json.encodeToString(UserSession.serializer(), value = t).encodeToByteArray())
    }
}