package com.example.tbcexercises.di

import com.example.tbcexercises.data.repository.PostRepositoryImpl
import com.example.tbcexercises.data.repository.StoryRepositoryImpl
import com.example.tbcexercises.domain.repository.PostRepository
import com.example.tbcexercises.domain.repository.StoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindPostRepository(
        impl: PostRepositoryImpl
    ): PostRepository

    @Binds
    @ViewModelScoped
    abstract fun bindStoriesRepository(
        impl: StoryRepositoryImpl
    ): StoryRepository

}