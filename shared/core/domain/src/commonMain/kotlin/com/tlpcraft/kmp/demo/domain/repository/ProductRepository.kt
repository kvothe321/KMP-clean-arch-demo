package com.tlpcraft.kmp.demo.domain.repository

import com.tlpcraft.kmp.demo.domain.model.ProductPreview

interface ProductRepository {
    suspend fun getProducts(limit: Int, skip: Int): Result<List<ProductPreview>>

    suspend fun searchProducts(query: String, limit: Int, skip: Int): Result<List<ProductPreview>>

    suspend fun getProduct(id: Int): Result<ProductPreview>
}
