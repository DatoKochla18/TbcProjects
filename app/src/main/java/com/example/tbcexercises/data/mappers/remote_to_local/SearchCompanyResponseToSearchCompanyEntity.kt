package com.example.tbcexercises.data.mappers.remote_to_local

import com.example.tbcexercises.data.local.entity.search.SearchCompanyEntity
import com.example.tbcexercises.data.remote.response.search.SearchCompanyResponse


fun SearchCompanyResponse.toSearchCompanyEntity(): SearchCompanyEntity {
    return SearchCompanyEntity(
        name = this.name,
        companyImgUrl = this.companyImgUrl,
        productPrice = this.productPrice.toString()
    )
}