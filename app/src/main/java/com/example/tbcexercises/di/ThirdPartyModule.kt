package com.example.tbcexercises.di

import com.example.tbcexercises.data.third_pary_library.image_loader.GlideImageLoader
import com.example.tbcexercises.domain.third_party_library.image_loader.ImageLoader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ThirdPartyModule {

    @Binds
    @Singleton
    abstract fun bindImageLoader(impl: GlideImageLoader): ImageLoader
}