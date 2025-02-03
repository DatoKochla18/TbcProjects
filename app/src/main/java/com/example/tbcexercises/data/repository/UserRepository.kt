package com.example.tbcexercises.data.repository

import com.example.tbcexercises.data.model.local.User
import com.example.tbcexercises.data.model.local.UserDao
import com.example.tbcexercises.data.model.remote.UserApi
import com.example.tbcexercises.data.model.remote.UserListResponse
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
    }.flowOn(Dispatchers.IO)


    val users = userDao.getUsers()

    suspend fun addUsersToDatabase(users: List<User>) {
        userDao.insert(users)
    }

}