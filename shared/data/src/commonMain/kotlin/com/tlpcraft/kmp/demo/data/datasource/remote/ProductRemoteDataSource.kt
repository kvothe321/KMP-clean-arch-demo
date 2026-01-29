package com.tlpcraft.kmp.demo.data.datasource.remote

import com.tlpcraft.kmp.demo.data.model.Product

interface ProductRemoteDataSource {

    suspend fun getProducts(limit: Int): List<Product>

    suspend fun getProduct(id: Int): Product
}
