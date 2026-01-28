package com.tlpcraft.kmp.demo.data.repository

import com.tlpcraft.kmp.demo.data.networking.api.ProductApi
import com.tlpcraft.kmp.demo.domain.model.MyData
import com.tlpcraft.kmp.demo.domain.repository.ProductRepository

class ProductRepositoryImpl(private val productApi: ProductApi) : ProductRepository {

    override suspend fun getProducts(): MyData {
        val response = productApi.getProducts(3, 0)
        println("Response: $response")
        return MyData("Hello from MyRepositoryImpl!")
    }
}
