package com.tlpcraft.kmp.demo.data.networking.datasource

import com.tlpcraft.kmp.demo.data.datasource.remote.ProductRemoteDataSource
import com.tlpcraft.kmp.demo.data.model.Product
import com.tlpcraft.kmp.demo.data.networking.api.ProductApi

class ProductRemoteDataSourceImpl(private val productApi: ProductApi) : ProductRemoteDataSource {

    override suspend fun getProducts(limit: Int, skip: Int): List<Product> = productApi.getProducts(limit, skip)

    override suspend fun searchProducts(query: String, limit: Int, skip: Int): List<Product> = productApi.searchProducts(query, limit, skip)

    override suspend fun getProduct(id: Int): Product = productApi.getProduct(id)
}
