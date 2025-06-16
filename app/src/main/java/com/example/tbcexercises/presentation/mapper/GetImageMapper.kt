package com.example.tbcexercises.presentation.mapper

import com.example.tbcexercises.domain.model.GetImage
import com.example.tbcexercises.domain.model.GetImageDetail
import com.example.tbcexercises.domain.model.GetRGB
import com.example.tbcexercises.presentation.model.Image
import com.example.tbcexercises.presentation.model.ImageDetail
import com.example.tbcexercises.presentation.model.RGB


fun GetImage.toPresentation() = Image(
    id = id,
    title = title,
    userName = userName,
    hex = hex,
    badgeUrl = badgeUrl.replaceFirst("http://", "https://")
)

fun GetImageDetail.toPresentation() =
    ImageDetail(
        id,
        title,
        userName,
        dateCreated,
        rgb.toPresentation(),
        description,
        badgeUrl = badgeUrl.replaceFirst("http://", "https://")
    )

fun GetRGB.toPresentation() = RGB(red, green, blue)