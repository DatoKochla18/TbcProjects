package com.example.tbcexercises.data.repository

import android.util.Log
import com.example.tbcexercises.data.mappers.toStory
import com.example.tbcexercises.data.remote.service.StoryService
import com.example.tbcexercises.domain.model.Story
import com.example.tbcexercises.domain.repository.StoryRepository
import com.example.tbcexercises.util.network_helper.Resource
import com.example.tbcexercises.util.network_helper.handleNetworkRequest
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class StoryRepositoryImpl @Inject constructor(private val storyService: StoryService) :
    StoryRepository {

    override fun getStories(): Flow<Resource<List<Story>>> {
        Log.d("StoryRepository", "getStories() called")

        return handleNetworkRequest { storyService.getStories() }
            .map { resource ->
                when (resource) {
                    is Resource.Error -> Resource.Error(resource.message)
                    Resource.Loading -> Resource.Loading
                    is Resource.Success -> {
                        Log.d("data", resource.data.toString())
                        Resource.Success(resource.data.map { it.toStory() })
                    }
                }
            }
    }
}
