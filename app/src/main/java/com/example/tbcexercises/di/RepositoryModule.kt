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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideUserRepository(userApi: UserApi, appDatabase: AppDatabase): UserRepository {
        return UserRepositoryImpl(userApi = userApi, database = appDatabase)
    }

    @Provides
    fun provideLoginRepository(authApi: AuthApi): LoginRepository {
        return LoginRepositoryImpl(authApi = authApi)
    }

    @Provides
    fun provideRegisterRepository(authApi: AuthApi): RegisterRepository {
        return RegisterRepositoryImpl(authApi = authApi)
    }

    @Provides
    @Singleton
    fun provideUserSessionRepository(dataStore: DataStore<Preferences>): UserSessionRepository {
        return UserSessionRepositoryImpl(dataStore)
    }
}