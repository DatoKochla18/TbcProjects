package com.example.tbcexercises.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.tbcexercises.data.local.room.AppDatabase
import com.example.tbcexercises.data.remote.apis.AuthApi
import com.example.tbcexercises.data.remote.apis.UserApi
import com.example.tbcexercises.data.repository.LoginRepositoryImpl
import com.example.tbcexercises.data.repository.RegisterRepositoryImpl
import com.example.tbcexercises.data.repository.UserRepositoryImpl
import com.example.tbcexercises.data.repository.UserSessionRepositoryImpl
import com.example.tbcexercises.domain.repository.LoginRepository
import com.example.tbcexercises.domain.repository.RegisterRepository
import com.example.tbcexercises.domain.repository.UserRepository
import com.example.tbcexercises.domain.repository.UserSessionRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    abstract fun bindRegisterRepository(impl: RegisterRepositoryImpl): RegisterRepository

    @Binds
    @Singleton
    abstract fun bindUserSessionRepository(impl: UserSessionRepositoryImpl): UserSessionRepository
}