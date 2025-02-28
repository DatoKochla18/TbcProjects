package com.example.tbcexercises.data.mappers

import com.example.tbcexercises.data.remote.response.LocationResponse
import com.example.tbcexercises.domain.model.Location


fun LocationResponse.toLocation(): Location {
    return Location(lat = this.lat, lan = this.lan, address = this.address, title = this.title)
}