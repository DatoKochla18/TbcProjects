package com.example.tbcexercises.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.tbcexercises.App
import com.example.tbcexercises.User
import com.example.tbcexercises.UserList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

val Context.userListDataStore: DataStore<UserList> by dataStore(
    fileName = "user_list.pb",
    serializer = UserListSerializer
)

object ProtoDataStore {

    suspend fun saveUser(user: User) {
        App.CONTEXT?.userListDataStore?.updateData { currentData ->
            val oldData = currentData.usersList.toMutableList()

            val existingIndex = oldData.indexOfFirst { it.email == user.email }

            if (existingIndex == -1) {
                oldData.add(user)
            } else {
                oldData[existingIndex] = user
            }

            currentData.toBuilder()
                .clearUsers()
                .addAllUsers(oldData)
                .build()
        }

    }
    fun readUser(email: String): Flow<User> {
        return App.CONTEXT?.userListDataStore?.data?.map { it.usersList.first { user -> user.email == email } }
            ?: flow { User.getDefaultInstance() }
    }
}