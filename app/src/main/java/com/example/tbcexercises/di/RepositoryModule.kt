package com.example.tbcexercises.di

import com.example.tbcexercises.data.imageLoader.GlideImageLoader
import com.example.tbcexercises.data.repository.ProductRepositoryImpl
import com.example.tbcexercises.domain.imageLoader.ImageLoader
import com.example.tbcexercises.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository


}