package com.example.tbcexercises.domain.repository

import com.example.tbcexercises.domain.model.Story
import com.example.tbcexercises.util.network_helper.Resource
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun getStories(): Flow<Resource<List<Story>>>
}
