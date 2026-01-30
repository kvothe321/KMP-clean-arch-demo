package com.tlpcraft.kmp.demo.data.networking.api

import com.tlpcraft.kmp.demo.data.model.ProductDto

interface ProductApi {
    suspend fun getProducts(limit: Int, skip: Int): List<ProductDto>

    suspend fun searchProducts(query: String, limit: Int, skip: Int): List<ProductDto>

    suspend fun getProduct(id: Int): ProductDto
}
