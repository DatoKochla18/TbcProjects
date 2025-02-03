package com.example.tbcexercises.data.repository

import com.example.tbcexercises.data.local.User
import com.example.tbcexercises.data.local.UserDao
import com.example.tbcexercises.data.remote.UserApi
import com.example.tbcexercises.data.remote.UserListResponse
import com.example.tbcexercises.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository(private val userDao: UserDao, private val userApi: UserApi) {

    fun getUsersRemote(): Flow<Resource<UserListResponse>> = flow {
        emit(Resource.Loading)
        try {
            val response = userApi.getUsers()
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO) // to not block main thread nad it is recommended


    val users = userDao.getUsers() // to get users locally

    suspend fun addUsersToDatabase(users: List<User>) {
        userDao.insert(users)
    }

}