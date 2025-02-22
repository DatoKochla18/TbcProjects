package com.example.tbcexercises.presentation.home_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.model.Post
import com.example.tbcexercises.domain.model.Story
import com.example.tbcexercises.domain.repository.PostRepository
import com.example.tbcexercises.domain.repository.StoryRepository
import com.example.tbcexercises.util.network_helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val storyRepository: StoryRepository
) : ViewModel() {

    private val _stories =
        MutableStateFlow<Resource<List<Story>>?>(null)
    val stories: StateFlow<Resource<List<Story>>?> = _stories


    private val _posts =
        MutableStateFlow<Resource<List<Post>>?>(null)
    val posts: StateFlow<Resource<List<Post>>?> = _posts

    init {
        getStoriesProducts()
        getPostsProducts()
    }

    private fun getPostsProducts() {
        viewModelScope.launch {
            postRepository.getPosts().collectLatest { result ->
                Log.d("result", result.toString())
                _posts.value = result
            }
        }
    }

    private fun getStoriesProducts() {
        viewModelScope.launch {
            storyRepository.getStories().collectLatest { result ->
                _stories.value = result
            }
        }
    }

}