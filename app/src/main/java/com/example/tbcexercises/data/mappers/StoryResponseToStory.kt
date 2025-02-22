package com.example.tbcexercises.data.mappers

import com.example.tbcexercises.data.remote.response.StoryResponse
import com.example.tbcexercises.domain.model.Story


fun StoryResponse.toStory(): Story {
    return Story(id = this.id, cover = this.cover, title = this.title)
}