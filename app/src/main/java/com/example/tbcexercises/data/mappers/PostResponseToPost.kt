package com.example.tbcexercises.data.mappers

import com.example.tbcexercises.data.remote.response.PostResponse
import com.example.tbcexercises.domain.model.Post


fun PostResponse.toPost(): Post {
    return Post(
        id = this.id,
        comments = "${this.comments}  comments",
        images = this.images,
        likes = "${this.likes} likes",
        owner = this.owner.toOwner(),
        shareContent = this.shareContent,
        title = this.title,

        )
}


