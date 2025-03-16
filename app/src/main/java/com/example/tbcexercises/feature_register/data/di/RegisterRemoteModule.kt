package com.example.tbcexercises.feature_register.data.di

import com.example.tbcexercises.feature_register.data.remote.service.RegisterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RegisterRemoteModule {

    @Provides
    @Singleton
    fun provideRegisterApi(retrofit: Retrofit): RegisterService {
        return retrofit.create(RegisterService::class.java)
    }
}