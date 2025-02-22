package com.example.tbcexercises.data.repository

import android.util.Log
import com.example.tbcexercises.data.mappers.toPost
import com.example.tbcexercises.data.remote.service.PostService
import com.example.tbcexercises.domain.model.Post
import com.example.tbcexercises.domain.repository.PostRepository
import com.example.tbcexercises.util.network_helper.Resource
import com.example.tbcexercises.util.network_helper.handleNetworkRequest
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class PostRepositoryImpl @Inject constructor(private val postService: PostService) :
    PostRepository {
    override fun getPosts(): Flow<Resource<List<Post>>> {
        Log.d("PostRepository", "getPosts() called")

        return handleNetworkRequest { postService.getPosts() }.map { resource ->
            when (resource) {
                is Resource.Error -> Resource.Error(resource.message)
                Resource.Loading -> Resource.Loading
                is Resource.Success -> Resource.Success(resource.data.map { it.toPost() })
            }
        }
    }
}