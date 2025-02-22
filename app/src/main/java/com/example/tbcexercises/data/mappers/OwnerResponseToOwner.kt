package com.example.tbcexercises.data.mappers

import com.example.tbcexercises.data.remote.response.OwnerResponse
import com.example.tbcexercises.domain.model.Owner
import com.example.tbcexercises.util.extension.toDateString


fun OwnerResponse.toOwner(): Owner {
    return Owner(
        fullName = "$firstName $lastName",
        profile = this.profile,
        postDate = this.postDate.toDateString()
    )
}