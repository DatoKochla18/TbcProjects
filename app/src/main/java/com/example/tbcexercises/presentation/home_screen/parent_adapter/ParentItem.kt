package com.example.tbcexercises.presentation.home_screen.parent_adapter

import com.example.tbcexercises.domain.model.Post
import com.example.tbcexercises.domain.model.Story

sealed class ParentItem {
    data class PostItem(val data: List<Post>) : ParentItem()
    data class StoryItem(val data: List<Story>) : ParentItem()

}