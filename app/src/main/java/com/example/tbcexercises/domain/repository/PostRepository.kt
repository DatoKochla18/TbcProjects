package com.example.tbcexercises.domain.repository

import com.example.tbcexercises.domain.model.Post
import com.example.tbcexercises.util.network_helper.Resource
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<Resource<List<Post>>>
}