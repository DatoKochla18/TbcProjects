package com.example.tbcexercises.data.mapper

import com.example.tbcexercises.data.remote.response.ImageDetailResponse
import com.example.tbcexercises.data.remote.response.ImageResponse
import com.example.tbcexercises.data.remote.response.RGBResponse
import com.example.tbcexercises.domain.model.GetImage
import com.example.tbcexercises.domain.model.GetImageDetail
import com.example.tbcexercises.domain.model.GetRGB


fun ImageResponse.toDomain() = GetImage(id, title, userName,hex,badgeUrl)

fun ImageDetailResponse.toDomain() =
    GetImageDetail(id, title, userName, dateCreated, rgb.toDomain(), description, badgeUrl)

fun RGBResponse.toDomain() = GetRGB(red, green, blue)