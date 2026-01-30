package com.tlpcraft.kmp.demo.data.datasource.remote

import com.tlpcraft.kmp.demo.data.model.ProductDto

interface ProductRemoteDataSource {
    suspend fun getProducts(limit: Int, skip: Int): List<ProductDto>

    suspend fun searchProducts(query: String, limit: Int, skip: Int): List<ProductDto>

    suspend fun getProduct(id: Int): ProductDto
}
