package com.example.tbcexercises.feature_login.data.di

import com.example.tbcexercises.feature_login.data.remote.service.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginRemoteModule {
    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }
}