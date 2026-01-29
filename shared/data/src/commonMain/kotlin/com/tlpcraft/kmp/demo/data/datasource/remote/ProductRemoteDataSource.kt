package com.tlpcraft.kmp.demo.data.datasource.remote

import com.tlpcraft.kmp.demo.data.model.Product

interface ProductRemoteDataSource {
    suspend fun getProducts(limit: Int, skip: Int): List<Product>

    suspend fun searchProducts(query: String, limit: Int, skip: Int): List<Product>

    suspend fun getProduct(id: Int): Product
}
