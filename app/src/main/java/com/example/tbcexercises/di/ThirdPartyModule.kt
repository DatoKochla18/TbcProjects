package com.example.tbcexercises.di


import com.example.tbcexercises.util.image_loader.ImageLoader
import com.example.tbcexercises.util.image_loader.ImageLoaderImpl
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
    abstract fun bindImageLoader(
        impl: ImageLoaderImpl
    ): ImageLoader
}
