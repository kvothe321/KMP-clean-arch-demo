package com.tlpcraft.kmp.demo.domain.repository

import com.tlpcraft.kmp.demo.domain.model.ProductPreview

interface ProductRepository {

    suspend fun getProducts(limit: Int): Result<List<ProductPreview>>

    suspend fun getProduct(id: Int): Result<ProductPreview>
}
