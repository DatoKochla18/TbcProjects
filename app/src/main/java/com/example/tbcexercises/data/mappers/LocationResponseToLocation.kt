package com.example.tbcexercises.data.mappers

import com.example.tbcexercises.data.remote.response.LocationResponse
import com.example.tbcexercises.domain.model.Location


fun LocationResponse.toLocation(): Location {
    return Location(
        id = this.id,
        cover = this.cover,
        price = this.price,
        title = this.title,
        location = this.location,
        reactionCount = this.reactionCount,
        rate = this.rate
    )
}