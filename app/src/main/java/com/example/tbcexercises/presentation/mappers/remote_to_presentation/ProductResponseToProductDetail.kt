package com.example.tbcexercises.data.mappers.remote_to_presentation

import com.example.tbcexercises.data.remote.response.DetailProductResponse
import com.example.tbcexercises.domain.model.DetailProduct


fun DetailProductResponse.toProductDetail(): DetailProduct {
    return DetailProduct(
        productId = this.productId,
        company = this.company,
        companyImgUrl = this.companyImgUrl,
        productCategory = this.productCategory,
        productImgUrl = this.productImgUrl,
        productName = this.productName,
        productPrice = this.productPrice
    )
}