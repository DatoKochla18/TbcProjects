package com.example.tbcexercises.presentation.home_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.model.Post
import com.example.tbcexercises.domain.model.Story
import com.example.tbcexercises.domain.repository.PostRepository
import com.example.tbcexercises.domain.repository.StoryRepository
import com.example.tbcexercises.presentation.home_screen.parent_adapter.ParentItem
import com.example.tbcexercises.util.network_helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    postRepository: PostRepository,
    storyRepository: StoryRepository
) : ViewModel() {

    val parentItems: StateFlow<Resource<List<ParentItem>>?> =
        combine(
            postRepository.getPosts(),
            storyRepository.getStories()
        ) { posts, stories ->
            val postItems = if (posts is Resource.Success) ParentItem.PostItem(posts.data) else null
            val storyItems =
                if (stories is Resource.Success) ParentItem.StoryItem(stories.data) else null
            Resource.Success(listOfNotNull(storyItems, postItems))
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Resource.Loading
            )
}
